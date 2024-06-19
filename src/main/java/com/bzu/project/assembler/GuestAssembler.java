package com.bzu.project.assembler;

import com.bzu.project.controllers.GuestController;
import com.bzu.project.dto.GuestDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class GuestAssembler implements RepresentationModelAssembler<GuestDTO, GuestDTO> {

    @Override
    public static GuestDTO toModel(GuestDTO guestDTO) {
        guestDTO.add(linkTo(methodOn(GuestController.class).getGuestById(guestDTO.getId())).withSelfRel());
        return guestDTO;
    }

    @Override
    public static CollectionModel<GuestDTO> toCollectionModel(Iterable<? extends GuestDTO> entities) {
        CollectionModel<GuestDTO> guestDTOs = RepresentationModelAssembler.super.toCollectionModel(entities);
        guestDTOs.add(linkTo(methodOn(GuestController.class).getAllGuests()).withSelfRel());
        return guestDTOs;
    }
}