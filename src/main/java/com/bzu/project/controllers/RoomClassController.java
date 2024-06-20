package com.bzu.project.controllers;

import com.bzu.project.assembler.RoomClassAssembler;
import com.bzu.project.dto.RoomClassDTO;
import com.bzu.project.model.RoomClass;
import com.bzu.project.service.RoomClassService;
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
@RequestMapping(value = "/api/v1/room-classes", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class RoomClassController {

    private final RoomClassService roomClassService;
    private final RoomClassAssembler roomClassAssembler;

    @Operation(summary = "Get all room classes", description = "Retrieve a paginated list of all room classes")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    public CollectionModel<EntityModel<RoomClassDTO>> getAllRoomClasses(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<RoomClass> roomClasses = roomClassService.getAllRoomClasses(pageRequest);
        return roomClassAssembler.toCollectionModel(roomClasses)
                .add(linkTo(methodOn(RoomClassController.class).getAllRoomClasses(page, size)).withSelfRel());
    }

    @Operation(summary = "Get room class by ID", description = "Retrieve a room class by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved room class"),
            @ApiResponse(responseCode = "404", description = "Room class not found")
    })
    @GetMapping("/{id}")
    public EntityModel<RoomClassDTO> getRoomClassById(@PathVariable Long id) {
        RoomClass roomClass = roomClassService.getRoomClassById(id);
        return roomClassAssembler.toModel(roomClass);
    }

    @Operation(summary = "Create a new room class", description = "Add a new room class to the system")
    @ApiResponse(responseCode = "201", description = "Room class created successfully")
    @PostMapping
    public ResponseEntity<EntityModel<RoomClassDTO>> createRoomClass(@RequestBody RoomClassDTO roomClassDTO) {
        RoomClass roomClass = roomClassService.createRoomClass(roomClassDTO);
        URI location = linkTo(methodOn(RoomClassController.class).getRoomClassById(roomClass.getId())).toUri();
        return ResponseEntity.created(location).body(roomClassAssembler.toModel(roomClass));
    }

    @Operation(summary = "Update a room class", description = "Update details of an existing room class")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Room class updated successfully"),
            @ApiResponse(responseCode = "404", description = "Room class not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<RoomClassDTO>> updateRoomClass(@PathVariable Long id, @RequestBody RoomClassDTO roomClassDTO) {
        RoomClass updatedRoomClass = roomClassService.updateRoomClass(id, roomClassDTO);
        return ResponseEntity.ok(roomClassAssembler.toModel(updatedRoomClass));
    }

    @Operation(summary = "Delete a room class", description = "Delete a room class from the system")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Room class deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Room class not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomClass(@PathVariable Long id) {
        roomClassService.deleteRoomClass(id);
        return ResponseEntity.noContent().build();
    }
}
