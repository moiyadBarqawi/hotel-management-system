package com.bzu.project.dto;

import org.springframework.hateoas.RepresentationModel;

public class BookingRoomDTO extends RepresentationModel<BookingRoomDTO> {
    private long id;
    private Long bookingId;
    private Long roomId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}