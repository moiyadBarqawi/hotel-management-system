package com.bzu.project.dto;

import org.springframework.hateoas.RepresentationModel;

public class AddonDTO extends RepresentationModel<AddonDTO> {
    private Long id;
    private String addonName;
    private double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddonName() {
        return addonName;
    }

    public void setAddonName(String addonName) {
        this.addonName = addonName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}