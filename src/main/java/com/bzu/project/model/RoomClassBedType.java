package com.bzu.project.model;

import jakarta.persistence.*;

@Entity
public class RoomClassBedType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @MapsId("roomClassId")
    @JoinColumn(name = "room_class_id")
    private RoomClass roomClass;

    @ManyToOne
    @MapsId("bedTypeId")
    @JoinColumn(name = "bed_type_id")
    private BedType bedType;

    private int numBeds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoomClass getRoomClass() {
        return roomClass;
    }

    public void setRoomClass(RoomClass roomClass) {
        this.roomClass = roomClass;
    }

    public BedType getBedType() {
        return bedType;
    }

    public void setBedType(BedType bedType) {
        this.bedType = bedType;
    }

    public int getNumBeds() {
        return numBeds;
    }

    public void setNumBeds(int numBeds) {
        this.numBeds = numBeds;
    }
}
