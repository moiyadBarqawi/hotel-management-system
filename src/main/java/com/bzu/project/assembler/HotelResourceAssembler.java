package com.bzu.project.assembler;


import com.bzu.project.controllers.HotelController;
import com.bzu.project.dto.HotelDTO;
import com.bzu.project.model.Hotel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HotelResourceAssembler extends RepresentationModelAssemblerSupport<Hotel, HotelDTO> {

    public HotelResourceAssembler() {
        super(HotelController.class, HotelDTO.class);
    }

    @Override
    public HotelDTO toModel(Hotel entity) {
        HotelDTO hotelDTO = instantiateModel(entity);
        hotelDTO.setId(entity.getId());
        hotelDTO.setName(entity.getName());
        hotelDTO.setLocation(entity.getLocation());
        hotelDTO.setRating(entity.getRating());
        hotelDTO.add(linkTo(methodOn(HotelController.class).getHotelById(entity.getId())).withSelfRel());
        hotelDTO.add(linkTo(methodOn(HotelController.class).getAllHotels()).withRel("hotels"));
        return hotelDTO;
    }

    @Override
    public CollectionModel<HotelDTO> toCollectionModel(Iterable<? extends Hotel> entities) {
        return super.toCollectionModel(entities);
    }
}