package com.bzu.project.dto;

import org.springframework.hateoas.RepresentationModel;

public class RoomClassBedTypeDTO extends RepresentationModel<RoomClassBedTypeDTO> {
    private Long roomClassId;
    private Long bedTypeId;
    private int numBeds;

    public Long getRoomClassId() {
        return roomClassId;
    }

    public void setRoomClassId(Long roomClassId) {
        this.roomClassId = roomClassId;
    }

    public Long getBedTypeId() {
        return bedTypeId;
    }

    public void setBedTypeId(Long bedTypeId) {
        this.bedTypeId = bedTypeId;
    }

    public int getNumBeds() {
        return numBeds;
    }

    public void setNumBeds(int numBeds) {
        this.numBeds = numBeds;
    }
}