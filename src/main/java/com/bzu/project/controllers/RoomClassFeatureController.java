package com.bzu.project.controllers;

import com.bzu.project.assembler.RoomClassFeatureAssembler;
import com.bzu.project.dto.RoomClassFeatureDTO;
import com.bzu.project.model.RoomClassFeature;
import com.bzu.project.service.RoomClassFeatureService;
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
@RequestMapping(value = "/api/v1/room-class-features", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class RoomClassFeatureController {

    private final RoomClassFeatureService roomClassFeatureService;
    private final RoomClassFeatureAssembler roomClassFeatureAssembler;

    @Operation(summary = "Get all room class features", description = "Retrieve a list of all room class features")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    public CollectionModel<EntityModel<RoomClassFeatureDTO>> getAllRoomClassFeatures() {
        List<RoomClassFeatureDTO> roomClassFeatures = roomClassFeatureService.getAllRoomClassFeatures();
        List<RoomClassFeature> roomClassFeatureEntities = roomClassFeatures.stream()
                .map(dto -> roomClassFeatureService.convertToEntity(dto)) // Convert DTOs to entities for assembler
                .collect(Collectors.toList());
        return roomClassFeatureAssembler.toCollectionModel(roomClassFeatureEntities)
                .add(linkTo(methodOn(RoomClassFeatureController.class).getAllRoomClassFeatures()).withSelfRel());
    }

    @Operation(summary = "Get room class feature by ID", description = "Retrieve a room class feature by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved room class feature"),
            @ApiResponse(responseCode = "404", description = "Room class feature not found")
    })
    @GetMapping("/{roomClassId}/{featureId}")
    public EntityModel<RoomClassFeatureDTO> getRoomClassFeatureById(@PathVariable Long roomClassId, @PathVariable Long featureId) {
        RoomClassFeatureDTO roomClassFeature = roomClassFeatureService.getRoomClassFeatureById(roomClassId, featureId);
        RoomClassFeature roomClassFeatureEntity = roomClassFeatureService.convertToEntity(roomClassFeature);
        return roomClassFeatureAssembler.toModel(roomClassFeatureEntity);
    }

    @Operation(summary = "Create a new room class feature", description = "Add a new room class feature to the system")
    @ApiResponse(responseCode = "201", description = "Room class feature created successfully")
    @PostMapping
    public ResponseEntity<EntityModel<RoomClassFeatureDTO>> createRoomClassFeature(@RequestBody RoomClassFeatureDTO roomClassFeatureDTO) {
        RoomClassFeatureDTO createdRoomClassFeature = roomClassFeatureService.createRoomClassFeature(roomClassFeatureDTO);
        URI location = linkTo(methodOn(RoomClassFeatureController.class).getRoomClassFeatureById(createdRoomClassFeature.getRoomClassId(), createdRoomClassFeature.getFeatureId())).toUri();
        RoomClassFeature roomClassFeatureEntity = roomClassFeatureService.convertToEntity(createdRoomClassFeature);
        return ResponseEntity.created(location).body(roomClassFeatureAssembler.toModel(roomClassFeatureEntity));
    }

    @Operation(summary = "Update a room class feature", description = "Update details of an existing room class feature")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room class feature updated successfully"),
            @ApiResponse(responseCode = "404", description = "Room class feature not found")
    })
    @PutMapping("/{roomClassId}/{featureId}")
    public ResponseEntity<EntityModel<RoomClassFeatureDTO>> updateRoomClassFeature(@PathVariable Long roomClassId, @PathVariable Long featureId, @RequestBody RoomClassFeatureDTO roomClassFeatureDTO) {
        RoomClassFeatureDTO updatedRoomClassFeature = roomClassFeatureService.updateRoomClassFeature(roomClassId, featureId, roomClassFeatureDTO);
        RoomClassFeature roomClassFeatureEntity = roomClassFeatureService.convertToEntity(updatedRoomClassFeature);
        return ResponseEntity.ok(roomClassFeatureAssembler.toModel(roomClassFeatureEntity));
    }

    @Operation(summary = "Delete a room class feature", description = "Delete a room class feature from the system")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Room class feature deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Room class feature not found")
    })
    @DeleteMapping("/{roomClassId}/{featureId}")
    public ResponseEntity<Void> deleteRoomClassFeature(@PathVariable Long roomClassId, @PathVariable Long featureId) {
        roomClassFeatureService.deleteRoomClassFeature(roomClassId, featureId);
        return ResponseEntity.noContent().build();
    }
}
