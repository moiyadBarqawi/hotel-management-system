package com.bzu.project.model;

import jakarta.persistence.*;

@Entity
public class RoomClassFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @MapsId("roomClassId")
    @JoinColumn(name = "room_class_id")
    private RoomClass roomClass;

    @ManyToOne
    @MapsId("featureId")
    @JoinColumn(name = "feature_id")
    private Feature feature;

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

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }
}