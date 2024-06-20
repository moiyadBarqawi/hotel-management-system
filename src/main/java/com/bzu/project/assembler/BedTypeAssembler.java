package com.bzu.project.assembler;

import com.bzu.project.controllers.BedTypeController;
import com.bzu.project.dto.BedTypeDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BedTypeAssembler extends RepresentationModelAssemblerSupport<BedTypeDTO, BedTypeDTO> {

    public BedTypeAssembler() {
        super(BedTypeController.class, BedTypeDTO.class);
    }

    @Override
    public BedTypeDTO toModel(BedTypeDTO bedTypeDTO) {
        bedTypeDTO.add(linkTo(methodOn(BedTypeController.class).getBedTypeById(bedTypeDTO.getId())).withSelfRel());
        bedTypeDTO.add(linkTo(methodOn(BedTypeController.class).getAllBedTypes()).withRel("bed-types"));
        return bedTypeDTO;
    }

    @Override
    public CollectionModel<BedTypeDTO> toCollectionModel(Iterable<? extends BedTypeDTO> entities) {
        List<BedTypeDTO> bedTypeDTOs = ((List<BedTypeDTO>) entities).stream()
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(bedTypeDTOs, linkTo(methodOn(BedTypeController.class).getAllBedTypes()).withSelfRel());
    }
}
