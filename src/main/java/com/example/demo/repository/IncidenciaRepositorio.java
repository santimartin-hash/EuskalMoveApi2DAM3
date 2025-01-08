package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.Incidencia;

public interface IncidenciaRepositorio extends JpaRepository<Incidencia, Long> {
}
