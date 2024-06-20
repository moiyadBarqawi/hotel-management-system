package com.bzu.project.controllers;

import com.bzu.project.assembler.BedTypeAssembler;
import com.bzu.project.dto.BedTypeDTO;
import com.bzu.project.service.BedTypeService;
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
@RequestMapping(value = "/api/v1/bed-types", produces = MediaTypes.HAL_JSON_VALUE)
@OpenAPIDefinition(
        info = @Info(
                title = "Bed Types API",
                version = "1.0",
                description = "API for managing bed types",
                contact = @Contact(
                        name = "Support",
                        email = "support@example.com"
                )
        )
)
@RequiredArgsConstructor
public class BedTypeController {

    private final BedTypeService bedTypeService;
    private final BedTypeAssembler bedTypeAssembler;

    @Operation(summary = "Get all bed types", description = "Retrieve a list of all bed types")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    public CollectionModel<EntityModel<BedTypeDTO>> getAllBedTypes(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                   @RequestParam(name = "size", defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<BedTypeDTO> bedTypePage = bedTypeService.getAllBedTypes(pageRequest);
        List<EntityModel<BedTypeDTO>> bedTypes = bedTypePage.getContent().stream()
                .map(bedTypeAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(bedTypes,
                linkTo(methodOn(BedTypeController.class).getAllBedTypes(page, size)).withSelfRel());
    }

    @Operation(summary = "Get bed type by ID", description = "Retrieve a bed type by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved bed type"),
            @ApiResponse(responseCode = "404", description = "Bed type not found")
    })
    @GetMapping("/{id}")
    public EntityModel<BedTypeDTO> getBedTypeById(@PathVariable Long id) {
        BedTypeDTO bedType = bedTypeService.getBedTypeById(id);
        return bedTypeAssembler.toModel(bedType);
    }

    @Operation(summary = "Create a new bed type", description = "Add a new bed type to the system")
    @ApiResponse(responseCode = "201", description = "Bed type created successfully")
    @PostMapping
    public ResponseEntity<EntityModel<BedTypeDTO>> createBedType(@RequestBody BedTypeDTO bedTypeDTO) {
        BedTypeDTO createdBedType = bedTypeService.createBedType(bedTypeDTO);
        return ResponseEntity
                .created(linkTo(methodOn(BedTypeController.class).getBedTypeById(createdBedType.getId())).toUri())
                .body(bedTypeAssembler.toModel(createdBedType));
    }

    @Operation(summary = "Update a bed type", description = "Update details of an existing bed type")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Bed type updated successfully"),
            @ApiResponse(responseCode = "404", description = "Bed type not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<BedTypeDTO>> updateBedType(@PathVariable Long id, @RequestBody BedTypeDTO bedTypeDTO) {
        BedTypeDTO updatedBedType = bedTypeService.updateBedType(id, bedTypeDTO);
        return ResponseEntity.ok(bedTypeAssembler.toModel(updatedBedType));
    }

    @Operation(summary = "Delete a bed type", description = "Delete a bed type from the system")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Bed type deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Bed type not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBedType(@PathVariable Long id) {
        bedTypeService.deleteBedType(id);
        return ResponseEntity.noContent().build();
    }
}
