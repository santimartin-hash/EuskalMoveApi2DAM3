package com.example.demo.response;

import com.example.demo.DTO.CamaraDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CamaraApiResponse {

    private int totalItems;
    private int totalPages;
    private int currentPage;

    @JsonProperty("cameras")
    private List<CamaraDTO> cameras;

    // Getters and Setters
    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<CamaraDTO> getCameras() {
        return cameras;
    }

    public void setCameras(List<CamaraDTO> cameras) {
        this.cameras = cameras;
    }
}
