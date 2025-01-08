
package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.modelo.Incidencia;
import com.example.demo.repository.IncidenciaRepositorio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Incidencia Controller", description = "Controlador para mannejar las incidencias de la base de datos")
@RestController
@RequestMapping("/incidencias")
public class IncidenciaController {

    @Autowired
    private IncidenciaRepositorio incidenciaRepositorio;

    @Operation(summary = "Obtener una lista de todas las incidencias disponibles", 
               description = "Este endpoint devuelve una lista de todas las incidencias que cumplen con ciertos criterios de validación.")
    @GetMapping
    public List<Incidencia> obtenerIncidencias() {
        return incidenciaRepositorio.findAll().stream()
                .filter(incidencia -> incidencia.getIncidenceId() != null &&
                        incidencia.getSourceId() != null &&
                        incidencia.getIncidenceType() != null &&
                        incidencia.getAutonomousRegion() != null &&
                        incidencia.getProvince() != null &&
                        incidencia.getCause() != null &&
                        incidencia.getCityTown() != null &&
                        incidencia.getStartDate() != null &&
                        incidencia.getEndDate() != null &&
                        incidencia.getPkStart() != null &&
                        incidencia.getPkEnd() != null &&
                        incidencia.getDirection() != null &&
                        incidencia.getIncidenceName() != null &&
                        incidencia.getLatitude() != null &&
                        incidencia.getLongitude() != null)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Crear una nueva incidencia", 
            description = "Este endpoint permite crear una incidencia nueva en la base de datos.")

    @PostMapping
    public ResponseEntity<Incidencia> crearIncidencia(
            @Parameter(description = "Objeto Incidencia para almacenar en la base de datos", required = true) @RequestBody Incidencia incidencia) {
        Incidencia nuevaIncidencia = incidenciaRepositorio.save(incidencia);
        return ResponseEntity.ok(nuevaIncidencia);
    }

    @Operation(summary = "Actualizar una incidencia existente", 
               description = "Este endpoint permite actualizar una incidencia existente en la base de datos.")
    @PutMapping("/{id}")
    public ResponseEntity<Incidencia> modificarIncidencia(
            @Parameter(description = "ID de la incidencia a actualizar", required = true) @PathVariable long id,
            @Parameter(description = "Objeto Incidencia actualizado", required = true) @RequestBody Incidencia incidencia) {
        Optional<Incidencia> optionalIncidencia = incidenciaRepositorio.findById(id);
        if (optionalIncidencia.isPresent()) {
            Incidencia incidenciaExistente = optionalIncidencia.get();
            incidenciaExistente.setIncidenceId(incidencia.getIncidenceId());
            incidenciaExistente.setSourceId(incidencia.getSourceId());
            incidenciaExistente.setIncidenceType(incidencia.getIncidenceType());
            incidenciaExistente.setAutonomousRegion(incidencia.getAutonomousRegion());
            incidenciaExistente.setProvince(incidencia.getProvince());
            incidenciaExistente.setCause(incidencia.getCause());
            incidenciaExistente.setCityTown(incidencia.getCityTown());
            incidenciaExistente.setStartDate(incidencia.getStartDate());
            incidenciaExistente.setEndDate(incidencia.getEndDate());
            incidenciaExistente.setPkStart(incidencia.getPkStart());
            incidenciaExistente.setPkEnd(incidencia.getPkEnd());
            incidenciaExistente.setDirection(incidencia.getDirection());
            incidenciaExistente.setIncidenceName(incidencia.getIncidenceName());
            incidenciaExistente.setLatitude(incidencia.getLatitude());
            incidenciaExistente.setLongitude(incidencia.getLongitude());
            incidenciaRepositorio.save(incidenciaExistente);
            return ResponseEntity.ok(incidenciaExistente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar una incidencia existente", 
               description = "Este endpoint permite eliminar una incidencia existente de la base de datos.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarIncidencia(
            @Parameter(description = "ID de la incidencia a eliminar", required = true) @PathVariable long id) {
        if (incidenciaRepositorio.existsById(id)) {
            incidenciaRepositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    

	@Operation(summary = "Obtener una lista de incidencias disponibles por sourceId",
	           description = "Este endpoint devuelve una lista de incidencias disponibles que cumplen con ciertos criterios de validación y coinciden con el sourceId proporcionado.")
	@GetMapping("/disponibles/{sourceId}")
	public List<Incidencia> obtenerIncidenciasPorSourceId(
	        @Parameter(description = "ID de la fuente para filtrar las incidencias", required = true) @PathVariable String sourceId) {
	    return incidenciaRepositorio.findAll().stream()
	            .filter(incidencia -> incidencia.getIncidenceId() != null &&
	                    incidencia.getSourceId() != null &&
	                    incidencia.getSourceId().equals(sourceId) &&
	                    incidencia.getIncidenceType() != null &&
	                    incidencia.getAutonomousRegion() != null &&
	                    incidencia.getProvince() != null &&
	                    incidencia.getCause() != null &&
	                    incidencia.getCityTown() != null &&
	                    incidencia.getStartDate() != null &&
	                    incidencia.getEndDate() != null &&
	                    incidencia.getPkStart() != null &&
	                    incidencia.getPkEnd() != null &&
	                    incidencia.getDirection() != null &&
	                    incidencia.getIncidenceName() != null &&
	                    incidencia.getLatitude() != null &&
	                    incidencia.getLongitude() != null)
	            .collect(Collectors.toList());
	}

}
