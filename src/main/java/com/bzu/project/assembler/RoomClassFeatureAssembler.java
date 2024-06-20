package com.bzu.project.assembler;

import com.bzu.project.controllers.RoomClassFeatureController;
import com.bzu.project.dto.RoomClassFeatureDTO;
import com.bzu.project.model.RoomClassFeature;
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
public class RoomClassFeatureAssembler extends RepresentationModelAssemblerSupport<RoomClassFeature, EntityModel<RoomClassFeatureDTO>> {

    public RoomClassFeatureAssembler() {
        super(RoomClassFeatureController.class, (Class<EntityModel<RoomClassFeatureDTO>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<RoomClassFeatureDTO> toModel(RoomClassFeature roomClassFeature) {
        RoomClassFeatureDTO roomClassFeatureDTO = convertToDTO(roomClassFeature);
        return EntityModel.of(roomClassFeatureDTO,
                linkTo(methodOn(RoomClassFeatureController.class).getRoomClassFeatureById(roomClassFeatureDTO.getRoomClassId(), roomClassFeatureDTO.getFeatureId())).withSelfRel(),
                linkTo(methodOn(RoomClassFeatureController.class).getAllRoomClassFeatures()).withRel("room-class-features"));
    }

    @Override
    public CollectionModel<EntityModel<RoomClassFeatureDTO>> toCollectionModel(Iterable<? extends RoomClassFeature> entities) {
        List<EntityModel<RoomClassFeatureDTO>> roomClassFeatureDTOs = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(roomClassFeatureDTOs, linkTo(methodOn(RoomClassFeatureController.class).getAllRoomClassFeatures()).withSelfRel());
    }

    private RoomClassFeatureDTO convertToDTO(RoomClassFeature roomClassFeature) {
        RoomClassFeatureDTO roomClassFeatureDTO = new RoomClassFeatureDTO();
        roomClassFeatureDTO.setRoomClassId(roomClassFeature.getRoomClass().getId());
        roomClassFeatureDTO.setFeatureId(roomClassFeature.getFeature().getId());
        roomClassFeatureDTO.setRoomClass(roomClassFeature.getRoomClass());
        roomClassFeatureDTO.setFeature(roomClassFeature.getFeature());
        return roomClassFeatureDTO;
    }
}
