package com.bzu.project.controllers;

import com.bzu.project.assembler.RoomClassBedTypeAssembler;
import com.bzu.project.dto.RoomClassBedTypeDTO;
import com.bzu.project.service.RoomClassBedTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/v1/room-class-bed-types", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class RoomClassBedTypeController {

    private final RoomClassBedTypeService roomClassBedTypeService;
    private final RoomClassBedTypeAssembler roomClassBedTypeAssembler;

    @GetMapping
    public CollectionModel<RoomClassBedTypeDTO> getAllRoomClassBedTypes() {
        List<RoomClassBedTypeDTO> roomClassBedTypes = roomClassBedTypeService.getAllRoomClassBedTypes();
        return roomClassBedTypeAssembler.toCollectionModel(roomClassBedTypes)
                .add(linkTo(methodOn(RoomClassBedTypeController.class).getAllRoomClassBedTypes()).withSelfRel());
    }

    @GetMapping("/{roomClassId}/{bedTypeId}")
    public EntityModel<RoomClassBedTypeDTO> getRoomClassBedTypeById(@PathVariable Long roomClassId, @PathVariable Long bedTypeId) {
        RoomClassBedTypeDTO roomClassBedType = roomClassBedTypeService.getRoomClassBedTypeById(roomClassId, bedTypeId);
        return EntityModel.of(roomClassBedTypeAssembler.toModel(roomClassBedType));
    }

    @PostMapping
    public ResponseEntity<RoomClassBedTypeDTO> createRoomClassBedType(@RequestBody RoomClassBedTypeDTO roomClassBedTypeDTO) {
        RoomClassBedTypeDTO createdRoomClassBedType = roomClassBedTypeService.createRoomClassBedType(roomClassBedTypeDTO);
        return ResponseEntity
                .created(linkTo(methodOn(RoomClassBedTypeController.class).getRoomClassBedTypeById(createdRoomClassBedType.getRoomClassId(), createdRoomClassBedType.getBedTypeId())).toUri())
                .body(roomClassBedTypeAssembler.toModel(createdRoomClassBedType));
    }

    @PutMapping("/{roomClassId}/{bedTypeId}")
    public ResponseEntity<RoomClassBedTypeDTO> updateRoomClassBedType(@PathVariable Long roomClassId, @PathVariable Long bedTypeId, @RequestBody RoomClassBedTypeDTO roomClassBedTypeDTO) {
        RoomClassBedTypeDTO updatedRoomClassBedType = roomClassBedTypeService.updateRoomClassBedType(roomClassId, bedTypeId, roomClassBedTypeDTO);
        return ResponseEntity.ok(roomClassBedTypeAssembler.toModel(updatedRoomClassBedType));
    }

    @DeleteMapping("/{roomClassId}/{bedTypeId}")
    public ResponseEntity<Void> deleteRoomClassBedType(@PathVariable Long roomClassId, @PathVariable Long bedTypeId) {
        roomClassBedTypeService.deleteRoomClassBedType(roomClassId, bedTypeId);
        return ResponseEntity.noContent().build();
    }
}