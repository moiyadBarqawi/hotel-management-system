package com.bzu.project.controllers;

import com.bzu.project.assembler.RoomAssembler;
import com.bzu.project.dto.RoomDTO;
import com.bzu.project.service.RoomService;
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
@RequestMapping(value = "/api/v1/rooms", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final RoomAssembler roomAssembler;

    @GetMapping
    public CollectionModel<EntityModel<RoomDTO>> getAllRooms() {
        List<RoomDTO> rooms = roomService.getAllRooms();
        return roomAssembler.toCollectionModel(rooms)
                .add(linkTo(methodOn(RoomController.class).getAllRooms()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<RoomDTO> getRoomById(@PathVariable Long id) {
        RoomDTO room = roomService.getRoomById(id);
        return roomAssembler.toModel(room);
    }

    @PostMapping
    public ResponseEntity<EntityModel<RoomDTO>> createRoom(@RequestBody RoomDTO roomDTO) {
        RoomDTO createdRoom = roomService.createRoom(roomDTO);
        return ResponseEntity
                .created(linkTo(methodOn(RoomController.class).getRoomById(createdRoom.getId())).toUri())
                .body(roomAssembler.toModel(createdRoom));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<RoomDTO>> updateRoom(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
        RoomDTO updatedRoom = roomService.updateRoom(id, roomDTO);
        return ResponseEntity.ok(roomAssembler.toModel(updatedRoom));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
