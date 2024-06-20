package com.bzu.project.assembler;

import com.bzu.project.controllers.RoomClassBedTypeController;
import com.bzu.project.dto.RoomClassBedTypeDTO;
import com.bzu.project.model.RoomClassBedType;
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
public class RoomClassBedTypeAssembler extends RepresentationModelAssemblerSupport<RoomClassBedType, EntityModel<RoomClassBedTypeDTO>> {

    public RoomClassBedTypeAssembler() {
        super(RoomClassBedTypeController.class, (Class<EntityModel<RoomClassBedTypeDTO>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<RoomClassBedTypeDTO> toModel(RoomClassBedType roomClassBedType) {
        RoomClassBedTypeDTO roomClassBedTypeDTO = convertToDTO(roomClassBedType);
        return EntityModel.of(roomClassBedTypeDTO,
                linkTo(methodOn(RoomClassBedTypeController.class).getRoomClassBedTypeById(roomClassBedTypeDTO.getRoomClassId(), roomClassBedTypeDTO.getBedTypeId())).withSelfRel(),
                linkTo(methodOn(RoomClassBedTypeController.class).getAllRoomClassBedTypes()).withRel("room-class-bed-types"));
    }

    @Override
    public CollectionModel<EntityModel<RoomClassBedTypeDTO>> toCollectionModel(Iterable<? extends RoomClassBedType> entities) {
        List<EntityModel<RoomClassBedTypeDTO>> roomClassBedTypeDTOs = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(roomClassBedTypeDTOs, linkTo(methodOn(RoomClassBedTypeController.class).getAllRoomClassBedTypes()).withSelfRel());
    }

    private RoomClassBedTypeDTO convertToDTO(RoomClassBedType roomClassBedType) {
        RoomClassBedTypeDTO roomClassBedTypeDTO = new RoomClassBedTypeDTO();
        roomClassBedTypeDTO.setRoomClassId(roomClassBedType.getRoomClass().getId());
        roomClassBedTypeDTO.setBedTypeId(roomClassBedType.getBedType().getId());
        roomClassBedTypeDTO.setNumBeds(roomClassBedType.getNumBeds());
        return roomClassBedTypeDTO;
    }
}
