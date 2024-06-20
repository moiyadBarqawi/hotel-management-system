package com.bzu.project.assembler;

import com.bzu.project.controllers.HotelController;
import com.bzu.project.dto.HotelDTO;
import com.bzu.project.model.Hotel;
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
public class HotelResourceAssembler extends RepresentationModelAssemblerSupport<Hotel, EntityModel<HotelDTO>> {

    public HotelResourceAssembler() {
        super(HotelController.class, (Class<EntityModel<HotelDTO>>) (Class<?>) EntityModel.class); // Updated type cast
    }

    @Override
    public EntityModel<HotelDTO> toModel(Hotel entity) {
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setId(entity.getId());
        hotelDTO.setName(entity.getName());
        hotelDTO.setLocation(entity.getLocation());
        hotelDTO.setRating(entity.getRating());

        return EntityModel.of(hotelDTO,
                linkTo(methodOn(HotelController.class).getHotelById(entity.getId())).withSelfRel(),
                linkTo(methodOn(HotelController.class).getAllHotels(0, 10)).withRel("hotels"));
    }

    @Override
    public CollectionModel<EntityModel<HotelDTO>> toCollectionModel(Iterable<? extends Hotel> entities) {
        List<EntityModel<HotelDTO>> hotelDTOList = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(hotelDTOList, linkTo(methodOn(HotelController.class).getAllHotels(0, 10)).withSelfRel());
    }
}
