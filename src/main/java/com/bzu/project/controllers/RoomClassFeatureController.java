package com.bzu.project.controllers;

import com.bzu.project.assembler.RoomClassFeatureAssembler;
import com.bzu.project.dto.RoomClassFeatureDTO;
import com.bzu.project.service.RoomClassFeatureService;
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
@RequestMapping(value = "/api/v1/room-class-features", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class RoomClassFeatureController {

    private final RoomClassFeatureService roomClassFeatureService;
    private final RoomClassFeatureAssembler roomClassFeatureAssembler;

    @GetMapping
    public CollectionModel<RoomClassFeatureDTO> getAllRoomClassFeatures() {
        List<RoomClassFeatureDTO> roomClassFeatures = roomClassFeatureService.getAllRoomClassFeatures();
        return roomClassFeatureAssembler.toCollectionModel(roomClassFeatures)
                .add(linkTo(methodOn(RoomClassFeatureController.class).getAllRoomClassFeatures()).withSelfRel());
    }

    @GetMapping("/{roomClassId}/{featureId}")
    public EntityModel<RoomClassFeatureDTO> getRoomClassFeatureById(@PathVariable Long roomClassId, @PathVariable Long featureId) {
        RoomClassFeatureDTO roomClassFeature = roomClassFeatureService.getRoomClassFeatureById(roomClassId, featureId);
        return EntityModel.of(roomClassFeatureAssembler.toModel(roomClassFeature));
    }

    @PostMapping
    public ResponseEntity<RoomClassFeatureDTO> createRoomClassFeature(@RequestBody RoomClassFeatureDTO roomClassFeatureDTO) {
        RoomClassFeatureDTO createdRoomClassFeature = roomClassFeatureService.createRoomClassFeature(roomClassFeatureDTO);
        return ResponseEntity
                .created(linkTo(methodOn(RoomClassFeatureController.class).getRoomClassFeatureById(createdRoomClassFeature.getRoomClassId(), createdRoomClassFeature.getFeatureId())).toUri())
                .body(roomClassFeatureAssembler.toModel(createdRoomClassFeature));
    }

    @PutMapping("/{roomClassId}/{featureId}")
    public ResponseEntity<RoomClassFeatureDTO> updateRoomClassFeature(@PathVariable Long roomClassId, @PathVariable Long featureId, @RequestBody RoomClassFeatureDTO roomClassFeatureDTO) {
        RoomClassFeatureDTO updatedRoomClassFeature = roomClassFeatureService.updateRoomClassFeature(roomClassId, featureId, roomClassFeatureDTO);
        return ResponseEntity.ok(roomClassFeatureAssembler.toModel(updatedRoomClassFeature));
    }

    @DeleteMapping("/{roomClassId}/{featureId}")
    public ResponseEntity<Void> deleteRoomClassFeature(@PathVariable Long roomClassId, @PathVariable Long featureId) {
        roomClassFeatureService.deleteRoomClassFeature(roomClassId, featureId);
        return ResponseEntity.noContent().build();
    }
}
