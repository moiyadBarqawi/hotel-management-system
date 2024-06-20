package com.bzu.project.assembler;

import com.bzu.project.controllers.FloorController;
import com.bzu.project.dto.FloorDTO;
import com.bzu.project.model.Floor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FloorAssembler implements RepresentationModelAssembler<Floor, EntityModel<FloorDTO>> {

    @Override
    public EntityModel<FloorDTO> toModel(Floor floor) {
        FloorDTO floorDTO = convertToDto(floor);

        return EntityModel.of(floorDTO,
                linkTo(methodOn(FloorController.class).getFloorById(floorDTO.getId())).withSelfRel(),
                linkTo(methodOn(FloorController.class).getAllFloors()).withRel("floors"));
    }

    @Override
    public CollectionModel<EntityModel<FloorDTO>> toCollectionModel(Iterable<? extends Floor> entities) {
        List<EntityModel<FloorDTO>> floorDTOs = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(floorDTOs,
                linkTo(methodOn(FloorController.class).getAllFloors()).withSelfRel());
    }

    private FloorDTO convertToDto(Floor floor) {
        FloorDTO floorDTO = new FloorDTO();
        floorDTO.setId(floor.getId());
        floorDTO.setFloorNumber(floor.getFloorNumber());
        return floorDTO;
    }

    public CollectionModel<EntityModel<FloorDTO>> toCollectionModel(List<FloorDTO> dtos) {
        List<EntityModel<FloorDTO>> floorDTOs = dtos.stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(floorDTOs,
                linkTo(methodOn(FloorController.class).getAllFloors()).withSelfRel());
    }

    public EntityModel<FloorDTO> toModel(FloorDTO floorDTO) {
        return EntityModel.of(floorDTO,
                linkTo(methodOn(FloorController.class).getFloorById(floorDTO.getId())).withSelfRel(),
                linkTo(methodOn(FloorController.class).getAllFloors()).withRel("floors"));
    }
}
