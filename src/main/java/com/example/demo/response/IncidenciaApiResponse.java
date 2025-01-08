package com.example.demo.response;

import com.example.demo.DTO.IncidenciaDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class IncidenciaApiResponse {

    private int totalItems;
    private int totalPages;
    private int currentPage;

    @JsonProperty("incidences")
    private List<IncidenciaDTO> incidences;

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

    public List<IncidenciaDTO> getIncidences() {
        return incidences;
    }

    public void setIncidences(List<IncidenciaDTO> incidences) {
        this.incidences = incidences;
    }
}
