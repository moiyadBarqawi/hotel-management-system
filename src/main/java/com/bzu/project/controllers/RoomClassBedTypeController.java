package com.bzu.project.controllers;

import com.bzu.project.assembler.RoomClassBedTypeAssembler;
import com.bzu.project.dto.RoomClassBedTypeDTO;
import com.bzu.project.model.RoomClassBedType;
import com.bzu.project.service.RoomClassBedTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/v1/room-class-bed-types", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class RoomClassBedTypeController {

    private final RoomClassBedTypeService roomClassBedTypeService;
    private final RoomClassBedTypeAssembler roomClassBedTypeAssembler;

    @Operation(summary = "Get all room class bed types", description = "Retrieve a list of all room class bed types")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    public CollectionModel<EntityModel<RoomClassBedTypeDTO>> getAllRoomClassBedTypes() {
        List<RoomClassBedType> roomClassBedTypeEntities = roomClassBedTypeService.getAllRoomClassBedTypes().stream()
                .map(dto -> roomClassBedTypeService.convertToEntity(dto))
                .collect(Collectors.toList());
        return roomClassBedTypeAssembler.toCollectionModel(roomClassBedTypeEntities)
                .add(linkTo(methodOn(RoomClassBedTypeController.class).getAllRoomClassBedTypes()).withSelfRel());
    }

    @Operation(summary = "Get room class bed type by ID", description = "Retrieve a room class bed type by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved room class bed type"),
            @ApiResponse(responseCode = "404", description = "Room class bed type not found")
    })
    @GetMapping("/{roomClassId}/{bedTypeId}")
    public EntityModel<RoomClassBedTypeDTO> getRoomClassBedTypeById(@PathVariable Long roomClassId, @PathVariable Long bedTypeId) {
        RoomClassBedTypeDTO roomClassBedType = roomClassBedTypeService.getRoomClassBedTypeById(roomClassId, bedTypeId);
        RoomClassBedType roomClassBedTypeEntity = roomClassBedTypeService.convertToEntity(roomClassBedType);
        return EntityModel.of(roomClassBedTypeAssembler.toModel(roomClassBedTypeEntity));
    }

    @Operation(summary = "Create a new room class bed type", description = "Add a new room class bed type to the system")
    @ApiResponse(responseCode = "201", description = "Room class bed type created successfully")
    @PostMapping
    public ResponseEntity<EntityModel<RoomClassBedTypeDTO>> createRoomClassBedType(@RequestBody RoomClassBedTypeDTO roomClassBedTypeDTO) {
        RoomClassBedTypeDTO createdRoomClassBedType = roomClassBedTypeService.createRoomClassBedType(roomClassBedTypeDTO);
        URI location = linkTo(methodOn(RoomClassBedTypeController.class).getRoomClassBedTypeById(createdRoomClassBedType.getRoomClassId(), createdRoomClassBedType.getBedTypeId())).toUri();
        RoomClassBedType roomClassBedTypeEntity = roomClassBedTypeService.convertToEntity(createdRoomClassBedType);
        return ResponseEntity.created(location).body(EntityModel.of(roomClassBedTypeAssembler.toModel(roomClassBedTypeEntity)));
    }

    @Operation(summary = "Update a room class bed type", description = "Update details of an existing room class bed type")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room class bed type updated successfully"),
            @ApiResponse(responseCode = "404", description = "Room class bed type not found")
    })
    @PutMapping("/{roomClassId}/{bedTypeId}")
    public ResponseEntity<EntityModel<RoomClassBedTypeDTO>> updateRoomClassBedType(@PathVariable Long roomClassId, @PathVariable Long bedTypeId, @RequestBody RoomClassBedTypeDTO roomClassBedTypeDTO) {
        RoomClassBedTypeDTO updatedRoomClassBedType = roomClassBedTypeService.updateRoomClassBedType(roomClassId, bedTypeId, roomClassBedTypeDTO);
        RoomClassBedType roomClassBedTypeEntity = roomClassBedTypeService.convertToEntity(updatedRoomClassBedType);
        return ResponseEntity.ok(EntityModel.of(roomClassBedTypeAssembler.toModel(roomClassBedTypeEntity)));
    }

    @Operation(summary = "Delete a room class bed type", description = "Delete a room class bed type from the system")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Room class bed type deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Room class bed type not found")
    })
    @DeleteMapping("/{roomClassId}/{bedTypeId}")
    public ResponseEntity<Void> deleteRoomClassBedType(@PathVariable Long roomClassId, @PathVariable Long bedTypeId) {
        roomClassBedTypeService.deleteRoomClassBedType(roomClassId, bedTypeId);
        return ResponseEntity.noContent().build();
    }
}
