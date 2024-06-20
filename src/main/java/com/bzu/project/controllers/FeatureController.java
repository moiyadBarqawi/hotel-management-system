package com.bzu.project.controllers;

import com.bzu.project.assembler.FeatureAssembler;
import com.bzu.project.dto.FeatureDTO;
import com.bzu.project.service.FeatureService;
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
@RequestMapping(value = "/api/v1/features", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class FeatureController {

    private final FeatureService featureService;
    private final FeatureAssembler featureAssembler;

    @GetMapping
    public CollectionModel<FeatureDTO> getAllFeatures() {
        List<FeatureDTO> features = featureService.getAllFeatures();
        return featureAssembler.toCollectionModel(features);
    }

    @GetMapping("/{id}")
    public EntityModel<FeatureDTO> getFeatureById(@PathVariable Long id) {
        FeatureDTO feature = featureService.getFeatureById(id);
        return EntityModel.of(featureAssembler.toModel(feature));
    }

    @PostMapping
    public ResponseEntity<FeatureDTO> createFeature(@RequestBody FeatureDTO featureDTO) {
        FeatureDTO createdFeature = featureService.createFeature(featureDTO);
        return ResponseEntity
                .created(linkTo(methodOn(FeatureController.class).getFeatureById(createdFeature.getId())).toUri())
                .body(featureAssembler.toModel(createdFeature));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeatureDTO> updateFeature(@PathVariable Long id, @RequestBody FeatureDTO featureDTO) {
        FeatureDTO updatedFeature = featureService.updateFeature(id, featureDTO);
        return ResponseEntity.ok(featureAssembler.toModel(updatedFeature));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeature(@PathVariable Long id) {
        featureService.deleteFeature(id);
        return ResponseEntity.noContent().build();
    }
}
