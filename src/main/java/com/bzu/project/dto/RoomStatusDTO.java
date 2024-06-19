package com.bzu.project.dto;

import org.springframework.hateoas.RepresentationModel;

public class RoomStatusDTO extends RepresentationModel<RoomStatusDTO> {
    private Long id;
    private String statusName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}