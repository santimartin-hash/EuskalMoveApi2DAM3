package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.modelo.Incidencia;

public interface IncidenciaRepositorio extends JpaRepository<Incidencia, Long> {
	@Modifying
	@Transactional
	@Query("DELETE FROM Incidencia i WHERE i.incidenceId IS NOT NULL AND i.incidenceId <> ''")
	void deleteAllWithNonEmptyIncidenceId();


}
