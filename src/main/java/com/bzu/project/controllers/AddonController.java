package com.bzu.project.controllers;

import com.bzu.project.assembler.AddonAssembler;
import com.bzu.project.dto.AddonDTO;
import com.bzu.project.model.Addon;
import com.bzu.project.service.AddonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping(value = "/api/v1/addons", produces = MediaTypes.HAL_JSON_VALUE)
@OpenAPIDefinition(
        info = @Info(
                title = "Addons API",
                version = "1.0",
                description = "API for managing addons",
                contact = @Contact(
                        name = "Support",
                        email = "support@example.com"
                )
        )
)
@RequiredArgsConstructor
public class AddonController {

    private final AddonService addonService;
    private final AddonAssembler addonAssembler;

    @Operation(summary = "Get all addons", description = "Retrieve a list of all addons")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    public CollectionModel<EntityModel<AddonDTO>> getAllAddons(@RequestParam(name = "page", defaultValue = "0") int page,
                                                               @RequestParam(name = "size", defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<AddonDTO> addonPage = addonService.getAllAddons(pageRequest);
        List<AddonDTO> addons = addonPage.getContent();
        return addonAssembler.toCollectionModel(addons.stream().map(this::convertToEntity).collect(Collectors.toList()))
                .add(linkTo(methodOn(AddonController.class).getAllAddons(page, size)).withSelfRel());
    }

    @Operation(summary = "Get addon by ID", description = "Retrieve an addon by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved addon"),
            @ApiResponse(responseCode = "404", description = "Addon not found")
    })
    @GetMapping("/{id}")
    public EntityModel<AddonDTO> getAddonById(@PathVariable Long id) {
        AddonDTO addonDTO = addonService.getAddonById(id);
        return addonAssembler.toModel(convertToEntity(addonDTO));
    }

    @Operation(summary = "Create a new addon", description = "Add a new addon to the system")
    @ApiResponse(responseCode = "201", description = "Addon created successfully")
    @PostMapping
    public ResponseEntity<EntityModel<AddonDTO>> createAddon(@RequestBody AddonDTO addonDTO) {
        AddonDTO createdAddonDTO = addonService.createAddon(addonDTO);
        return ResponseEntity
                .created(linkTo(methodOn(AddonController.class).getAddonById(createdAddonDTO.getId())).toUri())
                .body(addonAssembler.toModel(convertToEntity(createdAddonDTO)));
    }

    @Operation(summary = "Update an addon", description = "Update details of an existing addon")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Addon updated successfully"),
            @ApiResponse(responseCode = "404", description = "Addon not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<AddonDTO>> updateAddon(@PathVariable Long id, @RequestBody AddonDTO addonDTO) {
        AddonDTO updatedAddonDTO = addonService.updateAddon(id, addonDTO);
        return ResponseEntity.ok(addonAssembler.toModel(convertToEntity(updatedAddonDTO)));
    }

    @Operation(summary = "Delete an addon", description = "Delete an addon from the system")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Addon deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Addon not found")
    })
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
