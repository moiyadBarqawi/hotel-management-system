package com.bzu.project.controllers;

import com.bzu.project.assembler.BedTypeAssembler;
import com.bzu.project.dto.BedTypeDTO;
import com.bzu.project.service.BedTypeService;
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
@RequestMapping(value = "/api/v1/bed-types", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class BedTypeController {

    private final BedTypeService bedTypeService;
    private final BedTypeAssembler bedTypeAssembler;

    @GetMapping
    public CollectionModel<BedTypeDTO> getAllBedTypes() {
        List<BedTypeDTO> bedTypes = bedTypeService.getAllBedTypes();
        return bedTypeAssembler.toCollectionModel(bedTypes);
    }

    @GetMapping("/{id}")
    public EntityModel<BedTypeDTO> getBedTypeById(@PathVariable Long id) {
        BedTypeDTO bedType = bedTypeService.getBedTypeById(id);
        return EntityModel.of(bedTypeAssembler.toModel(bedType));
    }

    @PostMapping
    public ResponseEntity<BedTypeDTO> createBedType(@RequestBody BedTypeDTO bedTypeDTO) {
        BedTypeDTO createdBedType = bedTypeService.createBedType(bedTypeDTO);
        return ResponseEntity
                .created(linkTo(methodOn(BedTypeController.class).getBedTypeById(createdBedType.getId())).toUri())
                .body(bedTypeAssembler.toModel(createdBedType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BedTypeDTO> updateBedType(@PathVariable Long id, @RequestBody BedTypeDTO bedTypeDTO) {
        BedTypeDTO updatedBedType = bedTypeService.updateBedType(id, bedTypeDTO);
        return ResponseEntity.ok(bedTypeAssembler.toModel(updatedBedType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBedType(@PathVariable Long id) {
        bedTypeService.deleteBedType(id);
        return ResponseEntity.noContent().build();
    }
}
