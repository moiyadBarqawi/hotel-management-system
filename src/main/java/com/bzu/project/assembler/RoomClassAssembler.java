package com.bzu.project.assembler;

import com.bzu.project.controllers.RoomClassController;
import com.bzu.project.dto.RoomClassDTO;
import com.bzu.project.model.RoomClass;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RoomClassAssembler extends RepresentationModelAssemblerSupport<RoomClass, EntityModel<RoomClassDTO>> {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    public RoomClassAssembler() {
        super(RoomClassController.class, (Class<EntityModel<RoomClassDTO>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<RoomClassDTO> toModel(RoomClass roomClass) {
        RoomClassDTO roomClassDTO = new RoomClassDTO();
        roomClassDTO.setId(roomClass.getId());
        roomClassDTO.setClassName(roomClass.getClassName());
        roomClassDTO.setBasePrice(roomClass.getBasePrice());

        return EntityModel.of(roomClassDTO,
                linkTo(methodOn(RoomClassController.class).getRoomClassById(roomClass.getId())).withSelfRel(),
                linkTo(methodOn(RoomClassController.class).getAllRoomClasses(DEFAULT_PAGE, DEFAULT_SIZE)).withRel("room-classes"));
    }

    @Override
    public CollectionModel<EntityModel<RoomClassDTO>> toCollectionModel(Iterable<? extends RoomClass> entities) {
        var roomClassDTOs = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(roomClassDTOs,
                linkTo(methodOn(RoomClassController.class).getAllRoomClasses(DEFAULT_PAGE, DEFAULT_SIZE)).withSelfRel());
    }
}
