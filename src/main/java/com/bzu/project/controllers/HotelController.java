package com.bzu.project.controllers;


import com.bzu.project.dto.HotelDTO;
import com.bzu.project.model.Hotel;
import com.bzu.project.assembler.HotelResourceAssembler;
import com.bzu.project.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/api/v1/hotels", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;
    private final HotelResourceAssembler assembler;

    @GetMapping
    public CollectionModel<HotelDTO> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        List<HotelDTO> hotelDTOs = hotels.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(hotelDTOs,
                linkTo(methodOn(HotelController.class).getAllHotels()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<HotelDTO> getHotelById(@PathVariable Long id) {
        Hotel hotel = hotelService.getHotelById(id);
        return EntityModel.of(assembler.toModel(hotel));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN_CREATE')")
    public ResponseEntity<HotelDTO> createHotel(@RequestBody HotelDTO hotelDTO) {
        Hotel hotel = hotelService.createHotel(hotelDTO);
        HotelDTO createdHotel = assembler.toModel(hotel);
        return ResponseEntity
                .created(linkTo(methodOn(HotelController.class).getHotelById(createdHotel.getId())).toUri())
                .body(createdHotel);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_UPDATE')")
    public ResponseEntity<HotelDTO> updateHotel(@PathVariable Long id, @RequestBody HotelDTO hotelDTO) {
        Hotel updatedHotel = hotelService.updateHotel(id, hotelDTO);
        return ResponseEntity.ok(assembler.toModel(updatedHotel));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_DELETE')")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }
}