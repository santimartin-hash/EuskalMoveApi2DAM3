package com.example.demo.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
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
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty("incidenceId")
    private String incidenceId;

    @JsonProperty("sourceId")
    private String sourceId;

    @JsonProperty("incidenceType")
    private String incidenceType;

    @JsonProperty("autonomousRegion")
    private String autonomousRegion;

    @JsonProperty("province")
    private String province;

    @JsonProperty("cause")
    private String cause;

    @JsonProperty("cityTown")
    private String cityTown;

    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("endDate")
    private String endDate;

    @JsonProperty("pkStart")
    private String pkStart;

    @JsonProperty("pkEnd")
    private String pkEnd;

    @JsonProperty("direction")
    private String direction;

    @Column(length = 5000)
    @JsonProperty("incidenceName")
    private String incidenceName;

    @JsonProperty("latitude")
    private String latitude;

    @JsonProperty("longitude")
    private String longitude;
}
