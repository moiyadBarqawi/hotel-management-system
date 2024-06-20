package com.bzu.project.controllers;

import com.bzu.project.assembler.RoomStatusAssembler;
import com.bzu.project.dto.RoomStatusDTO;
import com.bzu.project.model.RoomStatus;
import com.bzu.project.service.RoomStatusService;
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
@RequestMapping(value = "/api/v1/room-statuses", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class RoomStatusController {

    private final RoomStatusService roomStatusService;
    private final RoomStatusAssembler roomStatusAssembler;

    @GetMapping
    public CollectionModel<EntityModel<RoomStatusDTO>> getAllRoomStatuses() {
        List<RoomStatusDTO> roomStatuses = roomStatusService.getAllRoomStatuses();
        List<RoomStatus> roomStatusEntities = roomStatuses.stream()
                .map(dto -> roomStatusService.convertToEntity(dto))
                .collect(Collectors.toList());
        return roomStatusAssembler.toCollectionModel(roomStatusEntities)
                .add(linkTo(methodOn(RoomStatusController.class).getAllRoomStatuses()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<RoomStatusDTO> getRoomStatusById(@PathVariable Long id) {
        RoomStatusDTO roomStatus = roomStatusService.getRoomStatusById(id);
        RoomStatus roomStatusEntity = roomStatusService.convertToEntity(roomStatus);
        return roomStatusAssembler.toModel(roomStatusEntity);
    }

    @PostMapping
    public ResponseEntity<EntityModel<RoomStatusDTO>> createRoomStatus(@RequestBody RoomStatusDTO roomStatusDTO) {
        validateRoomStatusDTO(roomStatusDTO); // Add validation logic as needed

        RoomStatusDTO createdRoomStatus = roomStatusService.createRoomStatus(roomStatusDTO);
        URI location = linkTo(methodOn(RoomStatusController.class).getRoomStatusById(createdRoomStatus.getId())).toUri();
        RoomStatus roomStatusEntity = roomStatusService.convertToEntity(createdRoomStatus);
        return ResponseEntity.created(location).body(roomStatusAssembler.toModel(roomStatusEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<RoomStatusDTO>> updateRoomStatus(@PathVariable Long id, @RequestBody RoomStatusDTO roomStatusDTO) {
        validateRoomStatusDTO(roomStatusDTO); // Add validation logic as needed

        RoomStatusDTO updatedRoomStatus = roomStatusService.updateRoomStatus(id, roomStatusDTO);
        RoomStatus roomStatusEntity = roomStatusService.convertToEntity(updatedRoomStatus);
        return ResponseEntity.ok(roomStatusAssembler.toModel(roomStatusEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomStatus(@PathVariable Long id) {
        roomStatusService.deleteRoomStatus(id);
        return ResponseEntity.noContent().build();
    }

    // Example validation method
    private void validateRoomStatusDTO(RoomStatusDTO roomStatusDTO) {
        if (roomStatusDTO.getStatusName() == null || roomStatusDTO.getStatusName().isEmpty()) {
            throw new IllegalArgumentException("Status name cannot be null or empty");
        }
    }
}
