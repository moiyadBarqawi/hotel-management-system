package com.bzu.project.controllers;

import com.bzu.project.assembler.FloorAssembler;
import com.bzu.project.dto.FloorDTO;
import com.bzu.project.model.Floor;
import com.bzu.project.service.FloorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/v1/floors", produces = MediaTypes.HAL_JSON_VALUE)
@OpenAPIDefinition(
        info = @Info(
                title = "Floors API",
                version = "1.0",
                description = "API for managing floors",
                contact = @Contact(
                        name = "Support",
                        email = "support@example.com"
                )
        )
)
@RequiredArgsConstructor
public class FloorController {

    private final FloorService floorService;
    private final FloorAssembler floorAssembler;

    @Operation(summary = "Get all floors", description = "Retrieve a list of all floors")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    public CollectionModel<EntityModel<FloorDTO>> getAllFloors(@RequestParam(name = "page", defaultValue = "0") int page,
                                                               @RequestParam(name = "size", defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Floor> floorPage = floorService.getAllFloors(pageRequest);
        List<EntityModel<FloorDTO>> floors = floorPage.getContent().stream()
                .map(floorAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(floors, linkTo(methodOn(FloorController.class).getAllFloors(page, size)).withSelfRel());
    }

    @Operation(summary = "Get floor by ID", description = "Retrieve a floor by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved floor"),
            @ApiResponse(responseCode = "404", description = "Floor not found")
    })
    @GetMapping("/{id}")
    public EntityModel<FloorDTO> getFloorById(@PathVariable Long id) {
        FloorDTO floor = floorService.getFloorById(id);
        return floorAssembler.toDTO(floor);
    }

    @Operation(summary = "Create a new floor", description = "Add a new floor to the system")
    @ApiResponse(responseCode = "201", description = "Floor created successfully")
    @PostMapping
    public ResponseEntity<EntityModel<FloorDTO>> createFloor(@RequestBody FloorDTO floorDTO) {
        FloorDTO createdFloor = floorService.createFloor(floorDTO);
        return ResponseEntity
                .created(linkTo(methodOn(FloorController.class).getFloorById(createdFloor.getId())).toUri())
                .body(floorAssembler.toDTO(createdFloor));
    }

    @Operation(summary = "Update a floor", description = "Update details of an existing floor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Floor updated successfully"),
            @ApiResponse(responseCode = "404", description = "Floor not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<FloorDTO>> updateFloor(@PathVariable Long id, @RequestBody FloorDTO floorDTO) {
        FloorDTO updatedFloor = floorService.updateFloor(id, floorDTO);
        return ResponseEntity.ok(floorAssembler.toDTO(updatedFloor));
    }

    @Operation(summary = "Delete a floor", description = "Delete a floor from the system")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Floor deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Floor not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFloor(@PathVariable Long id) {
        floorService.deleteFloor(id);
        return ResponseEntity.noContent().build();
    }
}
