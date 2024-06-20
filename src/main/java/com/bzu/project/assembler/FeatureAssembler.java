package com.bzu.project.assembler;

import com.bzu.project.controllers.FeatureController;
import com.bzu.project.dto.FeatureDTO;
import com.bzu.project.model.Feature;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FeatureAssembler extends RepresentationModelAssemblerSupport<FeatureDTO, FeatureDTO> {

    public FeatureAssembler() {
        super(FeatureController.class, FeatureDTO.class);
    }

    @Override
    public FeatureDTO toModel(FeatureDTO featureDTO) {
        featureDTO.add(linkTo(methodOn(FeatureController.class).getFeatureById(featureDTO.getId())).withSelfRel());
        featureDTO.add(linkTo(methodOn(FeatureController.class).getAllFeatures()).withRel("features"));
        return featureDTO;
    }

    @Override
    public CollectionModel<FeatureDTO> toCollectionModel(Iterable<? extends FeatureDTO> entities) {
        List<FeatureDTO> featureDTOs = ((List<FeatureDTO>) entities).stream()
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(featureDTOs, linkTo(methodOn(FeatureController.class).getAllFeatures()).withSelfRel());
    }
}
