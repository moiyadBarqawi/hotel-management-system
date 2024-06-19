package com.bzu.project.dto;

import org.springframework.hateoas.RepresentationModel;

public class RoomClassFeatureDTO extends RepresentationModel<RoomClassFeatureDTO> {
    private Long roomClassId;
    private Long featureId;

    public Long getRoomClassId() {
        return roomClassId;
    }

    public void setRoomClassId(Long roomClassId) {
        this.roomClassId = roomClassId;
    }

    public Long getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
    }
}