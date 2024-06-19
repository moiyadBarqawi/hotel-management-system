package com.bzu.project.dto;


import org.springframework.hateoas.RepresentationModel;

public class BedTypeDTO extends RepresentationModel<BedTypeDTO> {
    private Long id;
    private String bedTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBedTypeName() {
        return bedTypeName;
    }

    public void setBedTypeName(String bedTypeName) {
        this.bedTypeName = bedTypeName;
    }
}