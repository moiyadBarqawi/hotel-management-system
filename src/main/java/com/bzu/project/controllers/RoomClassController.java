package com.bzu.project.controllers;

import com.bzu.project.assembler.RoomClassAssembler;
import com.bzu.project.dto.RoomClassDTO;
import com.bzu.project.service.RoomClassService;
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
@RequestMapping(value = "/api/v1/room-classes", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class RoomClassController {

    private final RoomClassService roomClassService;
    private final RoomClassAssembler roomClassAssembler;

    @GetMapping
    public CollectionModel<EntityModel<RoomClassDTO>> getAllRoomClasses() {
        List<RoomClassDTO> roomClasses = roomClassService.getAllRoomClasses();
        return roomClassAssembler.toCollectionModel(roomClasses)
                .add(linkTo(methodOn(RoomClassController.class).getAllRoomClasses()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<RoomClassDTO> getRoomClassById(@PathVariable Long id) {
        RoomClassDTO roomClass = roomClassService.getRoomClassById(id);
        return roomClassAssembler.toModel(roomClass);
    }

    @PostMapping
    public ResponseEntity<EntityModel<RoomClassDTO>> createRoomClass(@RequestBody RoomClassDTO roomClassDTO) {
        RoomClassDTO createdRoomClass = roomClassService.createRoomClass(roomClassDTO);
        return ResponseEntity
                .created(linkTo(methodOn(RoomClassController.class).getRoomClassById(createdRoomClass.getId())).toUri())
                .body(roomClassAssembler.toModel(createdRoomClass));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<RoomClassDTO>> updateRoomClass(@PathVariable Long id, @RequestBody RoomClassDTO roomClassDTO) {
        RoomClassDTO updatedRoomClass = roomClassService.updateRoomClass(id, roomClassDTO);
        return ResponseEntity.ok(roomClassAssembler.toModel(updatedRoomClass));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomClass(@PathVariable Long id) {
        roomClassService.deleteRoomClass(id);
        return ResponseEntity.noContent().build();
    }
}
