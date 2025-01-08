
package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modelo.Camara;
import com.example.demo.repository.CamaraRepositorio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Camara Controller", description = "Controlador para manejar las camaras de la base de datos")
@RestController
public class CamaraController {

    @Autowired
    private CamaraRepositorio camaraRepositorio;

    @Operation(summary = "Obtener una lista de todas las camaras disponibles",
               description = "Este endpoint devuelve una lista de todas las camaras.")
    @GetMapping("/camaras")
    public List<Camara> obtenerCamaras() {
        return camaraRepositorio.findAll();
    }

    @Operation(summary = "Obtener una lista de camaras disponibles",
               description = "Este endpoint devuelve una lista de camaras disponibles que cumplen con ciertos criterios de validación. (Que funcione la imagen)")
    @GetMapping("/camaras/disponibles")
    public List<Camara> obtenerCamarasDisponibles() {
        return camaraRepositorio.findAll().stream()
            .filter(camara -> {
                // Si la URL contiene "vitoria-gazteiz", incluir aunque tenga campos null
                if (camara.getUrlImage() != null && camara.getUrlImage().contains("vitoria-gasteiz")) {
                    return true;
                }
                // Caso contrario, verificar que ningún campo relevante sea null
                return camara.getCameraId() != null &&
                       camara.getSourceId() != null &&
                       camara.getUrlImage() != null &&
                       camara.getCameraName() != null &&
                       camara.getLatitude() != null &&
                       camara.getLongitude() != null &&
                       camara.getRoad() != null &&
                       camara.getKilometer() != null &&
                       camara.getAddress() != null;
            })
            .collect(Collectors.toList());
    }
    


	@Operation(summary = "Obtener una lista de camaras disponibles por sourceId",
	           description = "Este endpoint devuelve una lista de camaras disponibles que cumplen con ciertos criterios de validación y coinciden con el sourceId proporcionado.")
	@GetMapping("/camaras/disponibles/{sourceId}")
	public List<Camara> obtenerCamarasDisponiblesPorSourceId(
	        @Parameter(description = "ID de la fuente para filtrar las camaras", required = true) @PathVariable String sourceId) {
	    return camaraRepositorio.findAll().stream()
	        .filter(camara -> {
	            // Si la URL contiene "vitoria-gazteiz", incluir aunque tenga campos null y coincida el sourceId
	            if (camara.getUrlImage() != null && camara.getUrlImage().contains("vitoria-gasteiz") && camara.getSourceId().equals(sourceId)) {
	                return true;
	            }
	            // Caso contrario, verificar que ningún campo relevante sea null y que coincida el sourceId
	            return camara.getCameraId() != null &&
	                   camara.getSourceId() != null &&
	                   camara.getSourceId().equals(sourceId) &&
	                   camara.getUrlImage() != null &&
	                   camara.getCameraName() != null &&
	                   camara.getLatitude() != null &&
	                   camara.getLongitude() != null &&
	                   camara.getRoad() != null &&
	                   camara.getKilometer() != null &&
	                   camara.getAddress() != null;
	        })
	        .collect(Collectors.toList());
	}


}
