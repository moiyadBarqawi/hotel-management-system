package com.bzu.project.controllers;

import com.bzu.project.assembler.BookingAssembler;
import com.bzu.project.dto.BookingDTO;
import com.bzu.project.service.BookingService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/v1/bookings", produces = "application/hal+json")
@OpenAPIDefinition(
        info = @Info(
                title = "Bookings API",
                version = "1.0",
                description = "API for managing bookings",
                contact = @Contact(
                        name = "Support",
                        email = "support@example.com"
                )
        )
)
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BookingAssembler bookingAssembler;

    @Operation(summary = "Get all bookings", description = "Retrieve a list of all bookings")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    public CollectionModel<EntityModel<BookingDTO>> getAllBookings(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                   @RequestParam(name = "size", defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<BookingDTO> bookingPage = bookingService.getAllBookings(pageRequest);
        List<EntityModel<BookingDTO>> bookings = bookingPage.getContent().stream()
                .map(bookingAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(bookings, linkTo(methodOn(BookingController.class).getAllBookings(page, size)).withSelfRel());
    }

    @Operation(summary = "Get booking by ID", description = "Retrieve a booking by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved booking"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @GetMapping("/{id}")
    public EntityModel<BookingDTO> getBookingById(@PathVariable Long id) {
        BookingDTO booking = bookingService.getBookingById(id);
        return bookingAssembler.toModel(booking);
    }

    @Operation(summary = "Create a new booking", description = "Add a new booking to the system")
    @ApiResponse(responseCode = "201", description = "Booking created successfully")
    @PostMapping
    public ResponseEntity<EntityModel<BookingDTO>> createBooking(@RequestBody BookingDTO bookingDTO) {
        BookingDTO createdBooking = bookingService.createBooking(bookingDTO);
        return ResponseEntity
                .created(linkTo(methodOn(BookingController.class).getBookingById(createdBooking.getId())).toUri())
                .body(bookingAssembler.toModel(createdBooking));
    }

    @Operation(summary = "Update a booking", description = "Update details of an existing booking")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking updated successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<BookingDTO>> updateBooking(@PathVariable Long id, @RequestBody BookingDTO bookingDTO) {
        BookingDTO updatedBooking = bookingService.updateBooking(id, bookingDTO);
        return ResponseEntity.ok(bookingAssembler.toModel(updatedBooking));
    }

    @Operation(summary = "Delete a booking", description = "Delete a booking from the system")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Booking deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}
