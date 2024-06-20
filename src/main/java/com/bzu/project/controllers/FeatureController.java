package com.bzu.project.controllers;

import com.bzu.project.assembler.FeatureAssembler;
import com.bzu.project.dto.FeatureDTO;
import com.bzu.project.service.FeatureService;
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
@RequestMapping(value = "/api/v1/features", produces = MediaTypes.HAL_JSON_VALUE)
@OpenAPIDefinition(
        info = @Info(
                title = "Features API",
                version = "1.0",
                description = "API for managing features",
                contact = @Contact(
                        name = "Support",
                        email = "support@example.com"
                )
        )
)
@RequiredArgsConstructor
public class FeatureController {

    private final FeatureService featureService;
    private final FeatureAssembler featureAssembler;

    @Operation(summary = "Get all features", description = "Retrieve a list of all features")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    public CollectionModel<EntityModel<FeatureDTO>> getAllFeatures(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                   @RequestParam(name = "size", defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<FeatureDTO> featurePage = featureService.getAllFeatures(pageRequest);
        List<EntityModel<FeatureDTO>> features = featurePage.getContent().stream()
                .map(featureAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(features, linkTo(methodOn(FeatureController.class).getAllFeatures(page, size)).withSelfRel());
    }

    @Operation(summary = "Get feature by ID", description = "Retrieve a feature by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved feature"),
            @ApiResponse(responseCode = "404", description = "Feature not found")
    })
    @GetMapping("/{id}")
    public EntityModel<FeatureDTO> getFeatureById(@PathVariable Long id) {
        FeatureDTO feature = featureService.getFeatureById(id);
        return featureAssembler.toModel(feature);
    }

    @Operation(summary = "Create a new feature", description = "Add a new feature to the system")
    @ApiResponse(responseCode = "201", description = "Feature created successfully")
    @PostMapping
    public ResponseEntity<EntityModel<FeatureDTO>> createFeature(@RequestBody FeatureDTO featureDTO) {
        FeatureDTO createdFeature = featureService.createFeature(featureDTO);
        return ResponseEntity
                .created(linkTo(methodOn(FeatureController.class).getFeatureById(createdFeature.getId())).toUri())
                .body(featureAssembler.toModel(createdFeature));
    }

    @Operation(summary = "Update a feature", description = "Update details of an existing feature")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Feature updated successfully"),
            @ApiResponse(responseCode = "404", description = "Feature not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<FeatureDTO>> updateFeature(@PathVariable Long id, @RequestBody FeatureDTO featureDTO) {
        FeatureDTO updatedFeature = featureService.updateFeature(id, featureDTO);
        return ResponseEntity.ok(featureAssembler.toModel(updatedFeature));
    }

    @Operation(summary = "Delete a feature", description = "Delete a feature from the system")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Feature deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Feature not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeature(@PathVariable Long id) {
        featureService.deleteFeature(id);
        return ResponseEntity.noContent().build();
    }
}
