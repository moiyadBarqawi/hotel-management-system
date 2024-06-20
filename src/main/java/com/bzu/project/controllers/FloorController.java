package com.bzu.project.controllers;

import com.bzu.project.assembler.FloorAssembler;
import com.bzu.project.dto.FloorDTO;
import com.bzu.project.service.FloorService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/v1/floors", produces = MediaTypes.HAL_JSON_VALUE)
public class FloorController {

    private final FloorService floorService;
    private final FloorAssembler floorAssembler;

    public FloorController(FloorService floorService, FloorAssembler floorAssembler) {
        this.floorService = floorService;
        this.floorAssembler = floorAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<FloorDTO>> getAllFloors() {
        List<FloorDTO> floors = floorService.getAllFloors();
        return floorAssembler.toCollectionModel(floors)
                .add(linkTo(methodOn(FloorController.class).getAllFloors()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<FloorDTO> getFloorById(@PathVariable Long id) {
        FloorDTO floor = floorService.getFloorById(id);
        return floorAssembler.toModel(floor);
    }

    @PostMapping
    public ResponseEntity<EntityModel<FloorDTO>> createFloor(@RequestBody FloorDTO floorDTO) {
        FloorDTO createdFloor = floorService.createFloor(floorDTO);
        return ResponseEntity
                .created(linkTo(methodOn(FloorController.class).getFloorById(createdFloor.getId())).toUri())
                .body(floorAssembler.toModel(createdFloor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<FloorDTO>> updateFloor(@PathVariable Long id, @RequestBody FloorDTO floorDTO) {
        FloorDTO updatedFloor = floorService.updateFloor(id, floorDTO);
        return ResponseEntity.ok(floorAssembler.toModel(updatedFloor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFloor(@PathVariable Long id) {
        floorService.deleteFloor(id);
        return ResponseEntity.noContent().build();
    }
}
