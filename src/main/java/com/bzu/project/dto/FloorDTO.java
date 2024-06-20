package com.bzu.project.dto;

import org.springframework.hateoas.RepresentationModel;

public class FloorDTO extends RepresentationModel<FloorDTO> {
    private Long id;
    private String floorNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }
}
