package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CamaraDTO {

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