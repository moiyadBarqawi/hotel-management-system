package com.bzu.project.assembler;

import com.bzu.project.controllers.GuestController;
import com.bzu.project.dto.GuestDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GuestModelAssembler {

    public EntityModel<GuestDTO> toModel(GuestDTO guestDTO) {
        return EntityModel.of(guestDTO,
                linkTo(methodOn(GuestController.class).getGuestById(guestDTO.getId())).withSelfRel(),
                linkTo(methodOn(GuestController.class).getAllGuests(0, 10)).withRel("guests"));
    }

    public CollectionModel<EntityModel<GuestDTO>> toCollectionModel(Iterable<? extends GuestDTO> entities) {
        List<EntityModel<GuestDTO>> guestDTOs = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(guestDTOs, linkTo(methodOn(GuestController.class).getAllGuests(0, 10)).withSelfRel());
    }
}
