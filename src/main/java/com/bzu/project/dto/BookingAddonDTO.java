package com.bzu.project.dto;

import org.springframework.hateoas.RepresentationModel;

public class BookingAddonDTO extends RepresentationModel<BookingAddonDTO> {
    private long id;
    private Long bookingId;
    private Long addonId;

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

    public Long getAddonId() {
        return addonId;
    }

    public void setAddonId(Long addonId) {
        this.addonId = addonId;
    }
}
