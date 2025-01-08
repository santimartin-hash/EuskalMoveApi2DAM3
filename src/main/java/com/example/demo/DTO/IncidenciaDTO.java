package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncidenciaDTO {

    private String incidenceId;
    private String sourceId;
    private String incidenceType;
    private String autonomousRegion;
    private String province;
    private String cause;
    private String cityTown;
    private String startDate;
    private String endDate;
    private String pkStart;
    private String pkEnd;
    private String direction;
    private String incidenceName;
    private String latitude;
    private String longitude;

}
