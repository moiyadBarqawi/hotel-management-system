package com.bzu.project.controllers;

import com.bzu.project.assembler.GuestModelAssembler;
import com.bzu.project.dto.GuestDTO;
import com.bzu.project.service.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping(value = "/api/guests", produces = MediaTypes.HAL_JSON_VALUE)
@OpenAPIDefinition(
        info = @Info(
                title = "Guests API",
                version = "1.0",
                description = "API for managing guests",
                contact = @Contact(
                        name = "Support",
                        email = "support@example.com"
                )
        )
)
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;
    private final GuestModelAssembler guestModelAssembler;

    @Operation(summary = "Get all guests", description = "Retrieve a list of all guests")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    public CollectionModel<EntityModel<GuestDTO>> getAllGuests(@RequestParam(name = "page", defaultValue = "0") int page,
                                                               @RequestParam(name = "size", defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<GuestDTO> guestPage = guestService.getAllGuests(pageRequest);
        List<EntityModel<GuestDTO>> guests = guestPage.getContent().stream()
                .map(guestModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(guests, linkTo(methodOn(GuestController.class).getAllGuests(page, size)).withSelfRel());
    }

    @Operation(summary = "Get guest by ID", description = "Retrieve a guest by their ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved guest"),
            @ApiResponse(responseCode = "404", description = "Guest not found")
    })
    @GetMapping("/{id}")
    public EntityModel<GuestDTO> getGuestById(@PathVariable Long id) {
        GuestDTO guest = guestService.getGuestById(id);
        return guestModelAssembler.toModel(guest);
    }

    @Operation(summary = "Create a new guest", description = "Add a new guest to the system")
    @ApiResponse(responseCode = "201", description = "Guest created successfully")
    @PostMapping
    public ResponseEntity<EntityModel<GuestDTO>> createGuest(@RequestBody GuestDTO guestDTO) {
        GuestDTO createdGuest = guestService.createGuest(guestDTO);
        return ResponseEntity
                .created(linkTo(methodOn(GuestController.class).getGuestById(createdGuest.getId())).toUri())
                .body(guestModelAssembler.toModel(createdGuest));
    }

    @Operation(summary = "Update a guest", description = "Update details of an existing guest")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Guest updated successfully"),
            @ApiResponse(responseCode = "404", description = "Guest not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<GuestDTO>> updateGuest(@PathVariable Long id, @RequestBody GuestDTO guestDTO) {
        GuestDTO updatedGuest = guestService.updateGuest(id, guestDTO);
        return ResponseEntity.ok(guestModelAssembler.toModel(updatedGuest));
    }

    @Operation(summary = "Delete a guest", description = "Delete a guest from the system")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Guest deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Guest not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long id) {
        guestService.deleteGuest(id);
        return ResponseEntity.noContent().build();
    }
}
