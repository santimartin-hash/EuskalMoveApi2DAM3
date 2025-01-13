package com.example.demo.service;

import com.example.demo.SSLUtils;
import com.example.demo.DTO.IncidenciaDTO;
import com.example.demo.modelo.Incidencia;
import com.example.demo.repository.IncidenciaRepositorio;
import com.example.demo.response.IncidenciaApiResponse;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IncidenciaService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IncidenciaRepositorio incidenciaRepositorio;

    public void cargarDatosDesdeApiExterna() {
        // Eliminar todos los registros existentes en la base de datos
        incidenciaRepositorio.deleteAll();

        // Deshabilitar la verificación de SSL si es necesario
        SSLUtils.disableSslVerification();

        // URL de la API externa
        LocalDate today = LocalDate.now();
        String year = String.valueOf(today.getYear());
        String month = String.format("%02d", today.getMonthValue()); // Asegura que el mes tenga dos dígitos
        String day = String.format("%02d", today.getDayOfMonth());  // Asegura que el día tenga dos dígitos

        // Construir la URL con la fecha actual
        String url = String.format("https://api.euskadi.eus/traffic/v1.0/incidences/byDate/%s/%s/%s/byLocation/43.26271/-2.92528/20000", year, month, day);

        // Llama a la API y obtiene la respuesta como IncidenciaApiResponse
        IncidenciaApiResponse apiResponse = restTemplate.getForObject(url, IncidenciaApiResponse.class);

        // Procesar la respuesta de la API
        if (apiResponse != null && apiResponse.getIncidences() != null) {
            for (IncidenciaDTO incidenciaDTO : apiResponse.getIncidences()) {
                // Crear una instancia de Incidencia (entidad) y mapear los campos
                Incidencia incidencia = new Incidencia();
                incidencia.setIncidenceId(incidenciaDTO.getIncidenceId());
                incidencia.setSourceId(incidenciaDTO.getSourceId());
                incidencia.setIncidenceType(incidenciaDTO.getIncidenceType());
                incidencia.setAutonomousRegion(incidenciaDTO.getAutonomousRegion());
                incidencia.setProvince(incidenciaDTO.getProvince());
                incidencia.setCause(incidenciaDTO.getCause());
                incidencia.setCityTown(incidenciaDTO.getCityTown());
                incidencia.setStartDate(incidenciaDTO.getStartDate());
                incidencia.setEndDate(incidenciaDTO.getEndDate());
                incidencia.setPkStart(incidenciaDTO.getPkStart());
                incidencia.setPkEnd(incidenciaDTO.getPkEnd());
                incidencia.setDirection(incidenciaDTO.getDirection());
                incidencia.setIncidenceName(incidenciaDTO.getIncidenceName());
                incidencia.setLatitude(incidenciaDTO.getLatitude());
                incidencia.setLongitude(incidenciaDTO.getLongitude());

                // Guardar la incidencia en la base de datos
                incidenciaRepositorio.save(incidencia);
            }
        }
    }

    // Cargar datos automáticamente al inicio de la aplicación
    @EventListener(ContextRefreshedEvent.class)
    public void cargarDatosAlInicio() {
        cargarDatosDesdeApiExterna();
    }
    
    @Scheduled(cron = "0 0 0 * * ?")
    public void ejecutarIncidenciaService() {
        cargarDatosDesdeApiExterna();
    }
    
    
    
}