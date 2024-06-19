package com.bzu.project.dto;

import org.springframework.hateoas.RepresentationModel;

public class RoomClassDTO extends RepresentationModel<RoomClassDTO> {
    private Long id;
    private String className;
    private double basePrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }
}