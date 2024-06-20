package com.bzu.project.assembler;

import com.bzu.project.controllers.FeatureController;
import com.bzu.project.dto.FeatureDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FeatureAssembler extends RepresentationModelAssemblerSupport<FeatureDTO, EntityModel<FeatureDTO>> {

    public FeatureAssembler() {
        super(FeatureController.class, (Class<EntityModel<FeatureDTO>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<FeatureDTO> toModel(FeatureDTO featureDTO) {
        return EntityModel.of(featureDTO,
                linkTo(methodOn(FeatureController.class).getFeatureById(featureDTO.getId())).withSelfRel(),
                linkTo(methodOn(FeatureController.class).getAllFeatures(0, 10)).withRel("features"));
    }

    @Override
    public CollectionModel<EntityModel<FeatureDTO>> toCollectionModel(Iterable<? extends FeatureDTO> entities) {
        List<EntityModel<FeatureDTO>> featureDTOs = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(featureDTOs, linkTo(methodOn(FeatureController.class).getAllFeatures(0, 10)).withSelfRel());
    }
}
