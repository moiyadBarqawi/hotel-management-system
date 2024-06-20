package com.bzu.project.controllers;

import com.bzu.project.assembler.RoomAssembler;
import com.bzu.project.dto.RoomDTO;
import com.bzu.project.model.Room;
import com.bzu.project.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/v1/rooms", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final RoomAssembler roomAssembler;

    @Operation(summary = "Get all rooms", description = "Retrieve a paginated list of all rooms")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    public CollectionModel<EntityModel<RoomDTO>> getAllRooms(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Room> rooms = roomService.getAllRooms(pageRequest);
        return roomAssembler.toCollectionModel(rooms)
                .add(linkTo(methodOn(RoomController.class).getAllRooms(page, size)).withSelfRel());
    }

    @Operation(summary = "Get room by ID", description = "Retrieve a room by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved room"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @GetMapping("/{id}")
    public EntityModel<RoomDTO> getRoomById(@PathVariable Long id) {
        Room room = roomService.getRoomById(id);
        return roomAssembler.toModel(room);
    }

    @Operation(summary = "Create a new room", description = "Add a new room to the system")
    @ApiResponse(responseCode = "201", description = "Room created successfully")
    @PostMapping
    public ResponseEntity<EntityModel<RoomDTO>> createRoom(@RequestBody RoomDTO roomDTO) {
        Room room = roomService.createRoom(roomDTO);
        URI location = linkTo(methodOn(RoomController.class).getRoomById(room.getId())).toUri();
        return ResponseEntity.created(location).body(roomAssembler.toModel(room));
    }

    @Operation(summary = "Update a room", description = "Update details of an existing room")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room updated successfully"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<RoomDTO>> updateRoom(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
        Room updatedRoom = roomService.updateRoom(id, roomDTO);
        return ResponseEntity.ok(roomAssembler.toModel(updatedRoom));
    }

    @Operation(summary = "Delete a room", description = "Delete a room from the system")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Room deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Room not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
