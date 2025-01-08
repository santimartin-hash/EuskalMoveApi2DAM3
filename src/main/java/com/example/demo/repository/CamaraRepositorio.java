package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.Camara;

public interface CamaraRepositorio extends JpaRepository<Camara, Long> {
}
