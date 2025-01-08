package com.example.demo.modelo;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Camara {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    private Long id;
    
    private Long cameraId;

    private String sourceId;
    
    private String urlImage;

    private String cameraName;

    private String latitude;

    private String longitude;

    private String road;

    private String kilometer;

    private String address;

}