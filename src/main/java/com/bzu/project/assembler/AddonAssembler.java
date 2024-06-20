package com.bzu.project.assembler;

import com.bzu.project.controllers.AddonController;
import com.bzu.project.dto.AddonDTO;
import com.bzu.project.model.Addon;
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
public class AddonAssembler extends RepresentationModelAssemblerSupport<Addon, EntityModel<AddonDTO>> {
//testing commit
    public AddonAssembler() {
        super(AddonController.class, (Class<EntityModel<AddonDTO>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<AddonDTO> toModel(Addon addon) {
        AddonDTO addonDTO = new AddonDTO();
        addonDTO.setId(addon.getId());
        addonDTO.setAddonName(addon.getAddonName());
        addonDTO.setPrice(addon.getPrice()); // Ensure this matches the field name in Addon model
        return EntityModel.of(addonDTO,
                linkTo(methodOn(AddonController.class).getAddonById(addon.getId())).withSelfRel(),
                linkTo(methodOn(AddonController.class).getAllAddons()).withRel("addons"));
    }

    @Override
    public CollectionModel<EntityModel<AddonDTO>> toCollectionModel(Iterable<? extends Addon> entities) {
        List<EntityModel<AddonDTO>> addonDTOs = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(addonDTOs,
                linkTo(methodOn(AddonController.class).getAllAddons()).withSelfRel());
    }
}