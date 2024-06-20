package com.bzu.project.assembler;

import com.bzu.project.controllers.RoomClassBedTypeController;
import com.bzu.project.dto.RoomClassBedTypeDTO;
import com.bzu.project.model.RoomClassBedType;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RoomClassBedTypeAssembler extends RepresentationModelAssemblerSupport<RoomClassBedTypeDTO, RoomClassBedTypeDTO> {

    public RoomClassBedTypeAssembler() {
        super(RoomClassBedTypeController.class, RoomClassBedTypeDTO.class);
    }

    @Override
    public RoomClassBedTypeDTO toModel(RoomClassBedTypeDTO roomClassBedTypeDTO) {
        roomClassBedTypeDTO.add(linkTo(methodOn(RoomClassBedTypeController.class).getRoomClassBedTypeById(roomClassBedTypeDTO.getRoomClassId(), roomClassBedTypeDTO.getBedTypeId())).withSelfRel());
        roomClassBedTypeDTO.add(linkTo(methodOn(RoomClassBedTypeController.class).getAllRoomClassBedTypes()).withRel("room-class-bed-types"));
        return roomClassBedTypeDTO;
    }

    @Override
    public CollectionModel<RoomClassBedTypeDTO> toCollectionModel(Iterable<? extends RoomClassBedTypeDTO> entities) {
        List<RoomClassBedTypeDTO> roomClassBedTypeDTOs = ((List<RoomClassBedTypeDTO>) entities).stream()
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(roomClassBedTypeDTOs, linkTo(methodOn(RoomClassBedTypeController.class).getAllRoomClassBedTypes()).withSelfRel());
    }
}
