package com.bzu.project.controllers;

import com.bzu.project.assembler.HotelResourceAssembler;
import com.bzu.project.dto.HotelDTO;
import com.bzu.project.model.Hotel;
import com.bzu.project.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/v1/hotels", produces = MediaTypes.HAL_JSON_VALUE)
@OpenAPIDefinition(
        info = @Info(
                title = "Hotels API",
                version = "1.0",
                description = "API for managing hotels",
                contact = @Contact(
                        name = "Support",
                        email = "support@example.com"
                )
        )
)
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;
    private final HotelResourceAssembler assembler;

    @Operation(summary = "Get all hotels", description = "Retrieve a list of all hotels")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    public CollectionModel<EntityModel<HotelDTO>> getAllHotels(@RequestParam(name = "page", defaultValue = "0") int page,
                                                               @RequestParam(name = "size", defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Hotel> hotelPage = hotelService.getAllHotels(pageRequest);
        List<EntityModel<HotelDTO>> hotels = hotelPage.getContent().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(hotels, linkTo(methodOn(HotelController.class).getAllHotels(page, size)).withSelfRel());
    }

    @Operation(summary = "Get hotel by ID", description = "Retrieve a hotel by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved hotel"),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @GetMapping("/{id}")
    public EntityModel<HotelDTO> getHotelById(@PathVariable Long id) {
        Hotel hotel = hotelService.getHotelById(id);
        return assembler.toModel(hotel);
    }

    @Operation(summary = "Create a new hotel", description = "Add a new hotel to the system")
    @ApiResponse(responseCode = "201", description = "Hotel created successfully")
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN_CREATE')")
    public ResponseEntity<EntityModel<HotelDTO>> createHotel(@RequestBody HotelDTO hotelDTO) {
        Hotel hotel = hotelService.createHotel(hotelDTO);
        EntityModel<HotelDTO> createdHotel = assembler.toModel(hotel);
        return ResponseEntity
                .created(linkTo(methodOn(HotelController.class).getHotelById(createdHotel.getContent().getId())).toUri())
                .body(createdHotel);
    }

    @Operation(summary = "Update a hotel", description = "Update details of an existing hotel")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hotel updated successfully"),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_UPDATE')")
    public ResponseEntity<EntityModel<HotelDTO>> updateHotel(@PathVariable Long id, @RequestBody HotelDTO hotelDTO) {
        Hotel updatedHotel = hotelService.updateHotel(id, hotelDTO);
        return ResponseEntity.ok(assembler.toModel(updatedHotel));
    }

    @Operation(summary = "Delete a hotel", description = "Delete a hotel from the system")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Hotel deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Hotel not found")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN_DELETE')")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }
}
