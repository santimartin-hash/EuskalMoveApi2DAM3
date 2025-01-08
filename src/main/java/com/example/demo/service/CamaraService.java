package com.example.demo.service;

import com.example.demo.SSLUtils;
import com.example.demo.DTO.CamaraDTO;
import com.example.demo.modelo.Camara;
import com.example.demo.repository.CamaraRepositorio;
import com.example.demo.response.CamaraApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CamaraService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CamaraRepositorio camaraRepositorio;

    public void cargarDatosDesdeApiExterna() {
		camaraRepositorio.deleteAll();
		// Deshabilitar la verificación de SSL para poder llamar a la API
    	SSLUtils.disableSslVerification();
        // URL de la API externa
        String url = "https://api.euskadi.eus/traffic/v1.0/cameras/byLocation/43.21167/-2.719359/20000";

        
        // Llama a la API y obtiene la respuesta como CameraApiResponse
        CamaraApiResponse apiResponse = restTemplate.getForObject(url, CamaraApiResponse.class);

        if (apiResponse != null && apiResponse.getCameras() != null) {
            // Obtener la lista de cámaras desde la respuesta
            for (CamaraDTO camaraDTO : apiResponse.getCameras()) {
                Camara camara = new Camara();
                camara.setCameraId(camaraDTO.getCameraId());
                camara.setCameraName(camaraDTO.getCameraName());
                camara.setLatitude(camaraDTO.getLatitude());
                camara.setLongitude(camaraDTO.getLongitude());
                camara.setRoad(camaraDTO.getRoad());
                camara.setKilometer(camaraDTO.getKilometer());
                camara.setAddress(camaraDTO.getAddress());
                camara.setSourceId(camaraDTO.getSourceId());
                camara.setUrlImage(camaraDTO.getUrlImage());

                // Guardar la cámara en la base de datos
                camaraRepositorio.save(camara);
            }
        }
    }

    // Si queremos que los datos se carguen automáticamente cada vez que la aplicación arranca
    @EventListener(ContextRefreshedEvent.class)
    public void cargarDatosAlInicio() {
        cargarDatosDesdeApiExterna();
    }
}
