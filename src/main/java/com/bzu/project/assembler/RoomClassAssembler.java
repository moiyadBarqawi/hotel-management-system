package com.bzu.project.assembler;

import com.bzu.project.controllers.RoomClassController;
import com.bzu.project.dto.RoomClassDTO;
import com.bzu.project.model.RoomClass;
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
public class RoomClassAssembler implements RepresentationModelAssembler<RoomClass, EntityModel<RoomClassDTO>> {

    @Override
    public EntityModel<RoomClassDTO> toModel(RoomClass roomClass) {
        RoomClassDTO roomClassDTO = new RoomClassDTO();
        roomClassDTO.setId(roomClass.getId());
        roomClassDTO.setClassName(roomClass.getClassName());
        roomClassDTO.setBasePrice(roomClass.getBasePrice());

        return EntityModel.of(roomClassDTO,
                linkTo(methodOn(RoomClassController.class).getRoomClassById(roomClass.getId())).withSelfRel(),
                linkTo(methodOn(RoomClassController.class).getAllRoomClasses()).withRel("room-classes"));
    }

    @Override
    public CollectionModel<EntityModel<RoomClassDTO>> toCollectionModel(Iterable<? extends RoomClass> entities) {
        List<EntityModel<RoomClassDTO>> roomClassDTOs = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(roomClassDTOs,
                linkTo(methodOn(RoomClassController.class).getAllRoomClasses()).withSelfRel());
    }

    public CollectionModel<EntityModel<RoomClassDTO>> toCollectionModel(List<RoomClassDTO> dtos) {
        List<EntityModel<RoomClassDTO>> roomClassDTOs = dtos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(roomClassDTOs,
                linkTo(methodOn(RoomClassController.class).getAllRoomClasses()).withSelfRel());
    }

    public EntityModel<RoomClassDTO> toModel(RoomClassDTO roomClassDTO) {
        return EntityModel.of(roomClassDTO,
                linkTo(methodOn(RoomClassController.class).getRoomClassById(roomClassDTO.getId())).withSelfRel(),
                linkTo(methodOn(RoomClassController.class).getAllRoomClasses()).withRel("room-classes"));
    }
}
