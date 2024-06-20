package com.bzu.project.assembler;

import com.bzu.project.controllers.RoomController;
import com.bzu.project.dto.RoomDTO;
import com.bzu.project.model.Room;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RoomAssembler implements RepresentationModelAssembler<Room, EntityModel<RoomDTO>> {

    @Override
    public EntityModel<RoomDTO> toModel(Room room) {
        RoomDTO roomDTO = convertToDto(room);

        return EntityModel.of(roomDTO,
                linkTo(methodOn(RoomController.class).getRoomById(room.getId())).withSelfRel(),
                linkTo(methodOn(RoomController.class).getAllRooms(0, 10)).withRel("rooms"));
    }

    @Override
    public CollectionModel<EntityModel<RoomDTO>> toCollectionModel(Iterable<? extends Room> entities) {
        var roomDTOs = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(roomDTOs,
                linkTo(methodOn(RoomController.class).getAllRooms(0, 10)).withSelfRel());
    }

    private RoomDTO convertToDto(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setRoomNumber(room.getRoomNumber());
        roomDTO.setFloorId(room.getFloor().getId());
        roomDTO.setRoomClassId(room.getRoomClass().getId());
        roomDTO.setStatusId(room.getStatus().getId());
        return roomDTO;
    }
}
