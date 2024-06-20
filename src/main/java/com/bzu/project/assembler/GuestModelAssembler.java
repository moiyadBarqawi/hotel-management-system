package com.bzu.project.assembler;

import com.bzu.project.controllers.GuestController;
import com.bzu.project.dto.GuestDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class GuestModelAssembler {

    public static GuestDTO toModel(GuestDTO guestDTO) {
        guestDTO.add(linkTo(methodOn(GuestController.class).getGuestById(guestDTO.getId())).withSelfRel());
        return guestDTO;
    }

    public static CollectionModel<? extends GuestDTO> toCollectionModel(Iterable<? extends GuestDTO> entities) {
        CollectionModel<? extends GuestDTO> guestDTOs = CollectionModel.of(entities);
        guestDTOs.add(linkTo(methodOn(GuestController.class).getAllGuests()).withSelfRel());
        return guestDTOs;
    }
}
