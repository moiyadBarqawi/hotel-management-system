package com.bzu.project.assembler;

import com.bzu.project.controllers.BookingAddonController;
import com.bzu.project.dto.BookingAddonDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookingAddonAssembler extends RepresentationModelAssemblerSupport<BookingAddonDTO, EntityModel<BookingAddonDTO>> {

    public BookingAddonAssembler() {
        super(BookingAddonController.class, (Class<EntityModel<BookingAddonDTO>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<BookingAddonDTO> toModel(BookingAddonDTO bookingAddonDTO) {
        return EntityModel.of(bookingAddonDTO,
                linkTo(methodOn(BookingAddonController.class).getBookingAddonById(bookingAddonDTO.getBookingId(), bookingAddonDTO.getAddonId())).withSelfRel(),
                linkTo(methodOn(BookingAddonController.class).getAllBookingAddons(0, 10)).withRel("booking-addons"));
    }

    @Override
    public CollectionModel<EntityModel<BookingAddonDTO>> toCollectionModel(Iterable<? extends BookingAddonDTO> entities) {
        List<EntityModel<BookingAddonDTO>> bookingAddonDTOs = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(bookingAddonDTOs,
                linkTo(methodOn(BookingAddonController.class).getAllBookingAddons(0, 10)).withSelfRel());
    }
}
