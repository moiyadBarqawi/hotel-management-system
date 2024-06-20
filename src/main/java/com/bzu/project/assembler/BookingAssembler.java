package com.bzu.project.assembler;

import com.bzu.project.controllers.BookingController;
import com.bzu.project.dto.BookingDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookingAssembler implements RepresentationModelAssembler<BookingDTO, EntityModel<BookingDTO>> {

    @Override
    public EntityModel<BookingDTO> toModel(BookingDTO bookingDTO) {
        return EntityModel.of(bookingDTO,
                linkTo(methodOn(BookingController.class).getBookingById(bookingDTO.getId())).withSelfRel(),
                linkTo(methodOn(BookingController.class).getAllBookings()).withRel("bookings"));
    }

    @Override
    public CollectionModel<EntityModel<BookingDTO>> toCollectionModel(Iterable<? extends BookingDTO> entities) {
        CollectionModel<EntityModel<BookingDTO>> bookingDTOs = RepresentationModelAssembler.super.toCollectionModel(entities);
        bookingDTOs.add(linkTo(methodOn(BookingController.class).getAllBookings()).withSelfRel());
        return bookingDTOs;
    }
}
