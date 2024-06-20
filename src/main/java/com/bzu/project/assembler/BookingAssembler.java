package com.bzu.project.assembler;

import com.bzu.project.controllers.BookingController;
import com.bzu.project.dto.BookingDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookingAssembler implements RepresentationModelAssembler<BookingDTO, EntityModel<BookingDTO>> {

    @Override
    public EntityModel<BookingDTO> toModel(BookingDTO bookingDTO) {
        return EntityModel.of(bookingDTO,
                linkTo(methodOn(BookingController.class).getBookingById(bookingDTO.getId())).withSelfRel(),
                linkTo(methodOn(BookingController.class).getAllBookings(0, 10)).withRel("bookings"));
    }

    @Override
    public CollectionModel<EntityModel<BookingDTO>> toCollectionModel(Iterable<? extends BookingDTO> entities) {
        List<EntityModel<BookingDTO>> bookingDTOs = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(bookingDTOs,
                linkTo(methodOn(BookingController.class).getAllBookings(0, 10)).withSelfRel());
    }
}
