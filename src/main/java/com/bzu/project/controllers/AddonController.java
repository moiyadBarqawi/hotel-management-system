package com.bzu.project.controllers;

import com.bzu.project.assembler.AddonAssembler;
import com.bzu.project.dto.AddonDTO;
import com.bzu.project.model.Addon;
import com.bzu.project.service.AddonService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping(value = "/api/v1/addons", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class AddonController {

    private final AddonService addonService;
    private final AddonAssembler addonAssembler;

    @GetMapping
    public CollectionModel<EntityModel<AddonDTO>> getAllAddons() {
        List<AddonDTO> addons = addonService.getAllAddons();
        return addonAssembler.toCollectionModel(addons.stream().map(this::convertToEntity).collect(Collectors.toList()))
                .add(linkTo(methodOn(AddonController.class).getAllAddons()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<AddonDTO> getAddonById(@PathVariable Long id) {
        AddonDTO addonDTO = addonService.getAddonById(id);
        return addonAssembler.toModel(convertToEntity(addonDTO));
    }

    @PostMapping
    public ResponseEntity<EntityModel<AddonDTO>> createAddon(@RequestBody AddonDTO addonDTO) {
        AddonDTO createdAddonDTO = addonService.createAddon(addonDTO);
        return ResponseEntity
                .created(linkTo(methodOn(AddonController.class).getAddonById(createdAddonDTO.getId())).toUri())
                .body(addonAssembler.toModel(convertToEntity(createdAddonDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<AddonDTO>> updateAddon(@PathVariable Long id, @RequestBody AddonDTO addonDTO) {
        AddonDTO updatedAddonDTO = addonService.updateAddon(id, addonDTO);
        return ResponseEntity.ok(addonAssembler.toModel(convertToEntity(updatedAddonDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddon(@PathVariable Long id) {
        addonService.deleteAddon(id);
        return ResponseEntity.noContent().build();
    }

    private Addon convertToEntity(AddonDTO addonDTO) {
        Addon addon = new Addon();
        addon.setId(addonDTO.getId());
        addon.setAddonName(addonDTO.getAddonName());
        addon.setPrice(addonDTO.getPrice());
        return addon;
    }

    private AddonDTO convertToDTO(Addon addon) {
        AddonDTO addonDTO = new AddonDTO();
        addonDTO.setId(addon.getId());
        addonDTO.setAddonName(addon.getAddonName());
        addonDTO.setPrice(addon.getPrice());
        return addonDTO;
    }
}
