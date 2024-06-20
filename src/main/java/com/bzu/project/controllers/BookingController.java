package com.bzu.project.controllers;

import com.bzu.project.assembler.BookingAssembler;
import com.bzu.project.dto.BookingDTO;
import com.bzu.project.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/api/v1/bookings", produces = "application/hal+json")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BookingAssembler bookingAssembler;

    @GetMapping
    public CollectionModel<EntityModel<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.getAllBookings();
        return bookingAssembler.toCollectionModel(bookings)
                .add(linkTo(methodOn(BookingController.class).getAllBookings()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<BookingDTO> getBookingById(@PathVariable Long id) {
        BookingDTO booking = bookingService.getBookingById(id);
        return bookingAssembler.toModel(booking);
    }

    @PostMapping
    public ResponseEntity<EntityModel<BookingDTO>> createBooking(@RequestBody BookingDTO bookingDTO) {
        BookingDTO createdBooking = bookingService.createBooking(bookingDTO);
        return ResponseEntity
                .created(linkTo(methodOn(BookingController.class).getBookingById(createdBooking.getId())).toUri())
                .body(bookingAssembler.toModel(createdBooking));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<BookingDTO>> updateBooking(@PathVariable Long id, @RequestBody BookingDTO bookingDTO) {
        BookingDTO updatedBooking = bookingService.updateBooking(id, bookingDTO);
        return ResponseEntity.ok(bookingAssembler.toModel(updatedBooking));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}