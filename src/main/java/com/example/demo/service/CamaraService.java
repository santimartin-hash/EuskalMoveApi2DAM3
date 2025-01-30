package com.example.demo.service;

import com.example.demo.SSLUtils;
import com.example.demo.DTO.CamaraDTO;
import com.example.demo.modelo.Camara;
import com.example.demo.repository.CamaraRepositorio;
import com.example.demo.response.CamaraApiResponse;

import org.locationtech.proj4j.CRSFactory;
import org.locationtech.proj4j.CoordinateTransform;
import org.locationtech.proj4j.CoordinateTransformFactory;
import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CamaraService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CamaraRepositorio camaraRepositorio;

    private final CRSFactory crsFactory = new CRSFactory();
    private final CoordinateTransformFactory transformFactory = new CoordinateTransformFactory();
    private final CoordinateTransform transform;

    public CamaraService() {
        var utmCRS = crsFactory.createFromName("EPSG:25830");
        var wgs84CRS = crsFactory.createFromName("EPSG:4326");
        this.transform = transformFactory.createTransform(utmCRS, wgs84CRS);
    }

    private double[] convertirCoordenadas(double x, double y) {
        ProjCoordinate utmCoord = new ProjCoordinate(x, y);
        ProjCoordinate geoCoord = new ProjCoordinate();
        transform.transform(utmCoord, geoCoord);
        return new double[]{geoCoord.y, geoCoord.x}; // [latitude, longitude]
    }

    public void cargarDatosDesdeApiExterna() {
        camaraRepositorio.deleteAll();
        SSLUtils.disableSslVerification();

        String url = "https://api.euskadi.eus/traffic/v1.0/cameras/byLocation/43.21167/-2.719359/20000";

        CamaraApiResponse apiResponse = restTemplate.getForObject(url, CamaraApiResponse.class);

        if (apiResponse != null && apiResponse.getCameras() != null) {
            for (CamaraDTO camaraDTO : apiResponse.getCameras()) {
                Camara camara = new Camara();
                camara.setCameraId(camaraDTO.getCameraId());
                camara.setCameraName(camaraDTO.getCameraName());

                double latitude = Double.parseDouble(camaraDTO.getLatitude());
                double longitude = Double.parseDouble(camaraDTO.getLongitude());

                if (latitude > 90) {
                    // Las coordenadas están en UTM, necesitan conversión
                    double[] latLon = convertirCoordenadas(longitude, latitude);
                    camara.setLatitude(String.valueOf(latLon[0]));
                    camara.setLongitude(String.valueOf(latLon[1]));
                } else {
                    // Las coordenadas ya están en formato lat/lon
                    camara.setLatitude(String.valueOf(latitude));
                    camara.setLongitude(String.valueOf(longitude));
                }

                camara.setRoad(camaraDTO.getRoad());
                camara.setKilometer(camaraDTO.getKilometer());
                camara.setAddress(camaraDTO.getAddress());
                camara.setSourceId(camaraDTO.getSourceId());
                camara.setUrlImage(camaraDTO.getUrlImage());

                camaraRepositorio.save(camara);
            }
        }
    }

    @EventListener(ContextRefreshedEvent.class)
    public void cargarDatosAlInicio() {
        cargarDatosDesdeApiExterna();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void ejecutarCamaraService() {
        cargarDatosDesdeApiExterna();
    }
}
