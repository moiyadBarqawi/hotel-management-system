package com.bzu.project.assembler;

import com.bzu.project.controllers.BedTypeController;
import com.bzu.project.dto.BedTypeDTO;
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
public class BedTypeAssembler extends RepresentationModelAssemblerSupport<BedTypeDTO, EntityModel<BedTypeDTO>> {

    public BedTypeAssembler() {
        super(BedTypeController.class, (Class<EntityModel<BedTypeDTO>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<BedTypeDTO> toModel(BedTypeDTO bedTypeDTO) {
        return EntityModel.of(bedTypeDTO,
                linkTo(methodOn(BedTypeController.class).getBedTypeById(bedTypeDTO.getId())).withSelfRel(),
                linkTo(methodOn(BedTypeController.class).getAllBedTypes(0, 10)).withRel("bed-types"));
    }

    @Override
    public CollectionModel<EntityModel<BedTypeDTO>> toCollectionModel(Iterable<? extends BedTypeDTO> entities) {
        List<EntityModel<BedTypeDTO>> bedTypeDTOs = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(bedTypeDTOs,
                linkTo(methodOn(BedTypeController.class).getAllBedTypes(0, 10)).withSelfRel());
    }
}
