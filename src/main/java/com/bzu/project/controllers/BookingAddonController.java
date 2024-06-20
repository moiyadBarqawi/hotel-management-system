package com.bzu.project.controllers;

import com.bzu.project.assembler.BookingAddonAssembler;
import com.bzu.project.dto.BookingAddonDTO;
import com.bzu.project.service.BookingAddonService;
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
@RequestMapping(value = "/api/v1/booking-addons", produces = MediaTypes.HAL_JSON_VALUE)
@OpenAPIDefinition(
        info = @Info(
                title = "Booking Addons API",
                version = "1.0",
                description = "API for managing booking addons",
                contact = @Contact(
                        name = "Support",
                        email = "support@example.com"
                )
        )
)
@RequiredArgsConstructor
public class BookingAddonController {

    private final BookingAddonService bookingAddonService;
    private final BookingAddonAssembler bookingAddonAssembler;

    @Operation(summary = "Get all booking addons", description = "Retrieve a list of all booking addons")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    public CollectionModel<EntityModel<BookingAddonDTO>> getAllBookingAddons(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                             @RequestParam(name = "size", defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<BookingAddonDTO> bookingAddonPage = bookingAddonService.getAllBookingAddons(pageRequest);
        List<EntityModel<BookingAddonDTO>> bookingAddons = bookingAddonPage.getContent().stream()
                .map(bookingAddonAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(bookingAddons,
                linkTo(methodOn(BookingAddonController.class).getAllBookingAddons(page, size)).withSelfRel());
    }

    @Operation(summary = "Get booking addon by ID", description = "Retrieve a booking addon by its booking ID and addon ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved booking addon"),
            @ApiResponse(responseCode = "404", description = "Booking addon not found")
    })
    @GetMapping("/{bookingId}/{addonId}")
    public EntityModel<BookingAddonDTO> getBookingAddonById(@PathVariable Long bookingId, @PathVariable Long addonId) {
        BookingAddonDTO bookingAddon = bookingAddonService.getBookingAddonById(bookingId, addonId);
        return bookingAddonAssembler.toModel(bookingAddon);
    }

    @Operation(summary = "Create a new booking addon", description = "Add a new booking addon to the system")
    @ApiResponse(responseCode = "201", description = "Booking addon created successfully")
    @PostMapping
    public ResponseEntity<EntityModel<BookingAddonDTO>> createBookingAddon(@RequestBody BookingAddonDTO bookingAddonDTO) {
        BookingAddonDTO createdBookingAddon = bookingAddonService.createBookingAddon(bookingAddonDTO);
        return ResponseEntity
                .created(linkTo(methodOn(BookingAddonController.class).getBookingAddonById(createdBookingAddon.getBookingId(), createdBookingAddon.getAddonId())).toUri())
                .body(bookingAddonAssembler.toModel(createdBookingAddon));
    }

    @Operation(summary = "Update a booking addon", description = "Update details of an existing booking addon")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking addon updated successfully"),
            @ApiResponse(responseCode = "404", description = "Booking addon not found")
    })
    @PutMapping("/{bookingId}/{addonId}")
    public ResponseEntity<EntityModel<BookingAddonDTO>> updateBookingAddon(@PathVariable Long bookingId, @PathVariable Long addonId, @RequestBody BookingAddonDTO bookingAddonDTO) {
        BookingAddonDTO updatedBookingAddon = bookingAddonService.updateBookingAddon(bookingId, addonId, bookingAddonDTO);
        return ResponseEntity.ok(bookingAddonAssembler.toModel(updatedBookingAddon));
    }

    @Operation(summary = "Delete a booking addon", description = "Delete a booking addon from the system")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Booking addon deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Booking addon not found")
    })
    @DeleteMapping("/{bookingId}/{addonId}")
    public ResponseEntity<Void> deleteBookingAddon(@PathVariable Long bookingId, @PathVariable Long addonId) {
        bookingAddonService.deleteBookingAddon(bookingId, addonId);
        return ResponseEntity.noContent().build();
    }
}
