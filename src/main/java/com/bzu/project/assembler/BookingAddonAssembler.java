package com.bzu.project.assembler;

import com.bzu.project.controllers.BookingAddonController;
import com.bzu.project.dto.BookingAddonDTO;
import com.bzu.project.model.BookingAddon;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookingAddonAssembler extends RepresentationModelAssemblerSupport<BookingAddonDTO, BookingAddonDTO> {

    public BookingAddonAssembler() {
        super(BookingAddonController.class, BookingAddonDTO.class);
    }

    @Override
    public BookingAddonDTO toModel(BookingAddonDTO bookingAddonDTO) {
        bookingAddonDTO.add(linkTo(methodOn(BookingAddonController.class).getBookingAddonById(bookingAddonDTO.getBookingId(), bookingAddonDTO.getAddonId())).withSelfRel());
        bookingAddonDTO.add(linkTo(methodOn(BookingAddonController.class).getAllBookingAddons()).withRel("booking-addons"));
        return bookingAddonDTO;
    }

    @Override
    public CollectionModel<BookingAddonDTO> toCollectionModel(Iterable<? extends BookingAddonDTO> entities) {
        List<BookingAddonDTO> bookingAddonDTOs = ((List<BookingAddonDTO>) entities).stream()
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(bookingAddonDTOs, linkTo(methodOn(BookingAddonController.class).getAllBookingAddons()).withSelfRel());
    }
}
