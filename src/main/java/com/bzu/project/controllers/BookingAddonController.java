package com.bzu.project.controllers;

import com.bzu.project.assembler.BookingAddonAssembler;
import com.bzu.project.dto.BookingAddonDTO;
import com.bzu.project.service.BookingAddonService;
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
@RequestMapping(value = "/api/v1/booking-addons", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class BookingAddonController {

    private final BookingAddonService bookingAddonService;
    private final BookingAddonAssembler bookingAddonAssembler;

    @GetMapping
    public CollectionModel<BookingAddonDTO> getAllBookingAddons() {
        List<BookingAddonDTO> bookingAddons = bookingAddonService.getAllBookingAddons();
        return bookingAddonAssembler.toCollectionModel(bookingAddons)
                .add(linkTo(methodOn(BookingAddonController.class).getAllBookingAddons()).withSelfRel());
    }

    @GetMapping("/{bookingId}/{addonId}")
    public EntityModel<BookingAddonDTO> getBookingAddonById(@PathVariable Long bookingId, @PathVariable Long addonId) {
        BookingAddonDTO bookingAddon = bookingAddonService.getBookingAddonById(bookingId, addonId);
        return EntityModel.of(bookingAddonAssembler.toModel(bookingAddon));
    }

    @PostMapping
    public ResponseEntity<BookingAddonDTO> createBookingAddon(@RequestBody BookingAddonDTO bookingAddonDTO) {
        BookingAddonDTO createdBookingAddon = bookingAddonService.createBookingAddon(bookingAddonDTO);
        return ResponseEntity
                .created(linkTo(methodOn(BookingAddonController.class).getBookingAddonById(createdBookingAddon.getBookingId(), createdBookingAddon.getAddonId())).toUri())
                .body(bookingAddonAssembler.toModel(createdBookingAddon));
    }

    @PutMapping("/{bookingId}/{addonId}")
    public ResponseEntity<BookingAddonDTO> updateBookingAddon(@PathVariable Long bookingId, @PathVariable Long addonId, @RequestBody BookingAddonDTO bookingAddonDTO) {
        BookingAddonDTO updatedBookingAddon = bookingAddonService.updateBookingAddon(bookingId, addonId, bookingAddonDTO);
        return ResponseEntity.ok(bookingAddonAssembler.toModel(updatedBookingAddon));
    }

    @DeleteMapping("/{bookingId}/{addonId}")
    public ResponseEntity<Void> deleteBookingAddon(@PathVariable Long bookingId, @PathVariable Long addonId) {
        bookingAddonService.deleteBookingAddon(bookingId, addonId);
        return ResponseEntity.noContent().build();
    }
}
