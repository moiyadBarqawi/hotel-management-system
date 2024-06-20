package com.bzu.project.dto;

import com.bzu.project.model.Feature;
import com.bzu.project.model.RoomClass;
import org.springframework.hateoas.RepresentationModel;

public class RoomClassFeatureDTO extends RepresentationModel<RoomClassFeatureDTO> {
    private Long roomClassId;
    private Long featureId;
    private RoomClass roomClass;
    private Feature feature;

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

    public RoomClass getRoomClass() {
        return roomClass;
    }

    public void setRoomClass(RoomClass roomClass) {
        this.roomClass = roomClass;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }
}
