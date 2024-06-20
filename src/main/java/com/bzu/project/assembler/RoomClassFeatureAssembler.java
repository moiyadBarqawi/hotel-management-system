package com.bzu.project.assembler;

import com.bzu.project.controllers.RoomClassFeatureController;
import com.bzu.project.dto.RoomClassFeatureDTO;
import com.bzu.project.model.RoomClassFeature;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RoomClassFeatureAssembler extends RepresentationModelAssemblerSupport<RoomClassFeature, RoomClassFeatureDTO> {

    public RoomClassFeatureAssembler() {
        super(RoomClassFeatureController.class, RoomClassFeatureDTO.class);
    }

    @Override
    public RoomClassFeatureDTO toModel(RoomClassFeature roomClassFeature) {
        RoomClassFeatureDTO roomClassFeatureDTO = instantiateModel(roomClassFeature);
        roomClassFeatureDTO.setRoomClassId(roomClassFeature.getRoomClass().getId());
        roomClassFeatureDTO.setFeatureId(roomClassFeature.getFeature().getId());
        roomClassFeatureDTO.add(linkTo(methodOn(RoomClassFeatureController.class).getRoomClassFeatureById(roomClassFeature.getRoomClass().getId(), roomClassFeature.getFeature().getId())).withSelfRel());
        roomClassFeatureDTO.add(linkTo(methodOn(RoomClassFeatureController.class).getAllRoomClassFeatures()).withRel("room-class-features"));
        return roomClassFeatureDTO;
    }

    @Override
    public CollectionModel<RoomClassFeatureDTO> toCollectionModel(Iterable<? extends RoomClassFeature> entities) {
        List<RoomClassFeatureDTO> dtos = super.toCollectionModel(entities).getContent().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(dtos, linkTo(methodOn(RoomClassFeatureController.class).getAllRoomClassFeatures()).withSelfRel());
    }

    public CollectionModel<RoomClassFeatureDTO> toCollectionModel(List<RoomClassFeatureDTO> dtos) {
        return CollectionModel.of(dtos, linkTo(methodOn(RoomClassFeatureController.class).getAllRoomClassFeatures()).withSelfRel());
    }

    public RoomClassFeatureDTO toModel(RoomClassFeatureDTO roomClassFeatureDTO) {
        roomClassFeatureDTO.add(linkTo(methodOn(RoomClassFeatureController.class).getRoomClassFeatureById(roomClassFeatureDTO.getRoomClassId(), roomClassFeatureDTO.getFeatureId())).withSelfRel());
        roomClassFeatureDTO.add(linkTo(methodOn(RoomClassFeatureController.class).getAllRoomClassFeatures()).withRel("room-class-features"));
        return roomClassFeatureDTO;
    }
}
