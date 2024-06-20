package com.bzu.project.assembler;

import com.bzu.project.controllers.RoomStatusController;
import com.bzu.project.dto.RoomStatusDTO;
import com.bzu.project.model.RoomStatus;
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
public class RoomStatusAssembler extends RepresentationModelAssemblerSupport<RoomStatus, EntityModel<RoomStatusDTO>> {

    public RoomStatusAssembler() {
        super(RoomStatusController.class, (Class<EntityModel<RoomStatusDTO>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<RoomStatusDTO> toModel(RoomStatus roomStatus) {
        RoomStatusDTO roomStatusDTO = convertToDTO(roomStatus);

        return EntityModel.of(roomStatusDTO,
                linkTo(methodOn(RoomStatusController.class).getRoomStatusById(roomStatusDTO.getId())).withSelfRel(),
                linkTo(methodOn(RoomStatusController.class).getAllRoomStatuses()).withRel("room-statuses"));
    }

    @Override
    protected EntityModel<RoomStatusDTO> instantiateModel(RoomStatus entity) {
        return super.instantiateModel(entity);
    }

    @Override
    public CollectionModel<EntityModel<RoomStatusDTO>> toCollectionModel(Iterable<? extends RoomStatus> entities) {
        List<EntityModel<RoomStatusDTO>> roomStatusDTOs = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(roomStatusDTOs,
                linkTo(methodOn(RoomStatusController.class).getAllRoomStatuses()).withSelfRel());
    }

    private RoomStatusDTO convertToDTO(RoomStatus roomStatus) {
        RoomStatusDTO roomStatusDTO = new RoomStatusDTO();
        roomStatusDTO.setId(roomStatus.getId());
        roomStatusDTO.setStatusName(roomStatus.getStatusName());
        return roomStatusDTO;
    }
}
