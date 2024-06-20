package com.bzu.project.controllers;

import com.bzu.project.assembler.BookingRoomAssembler;
import com.bzu.project.dto.BookingRoomDTO;
import com.bzu.project.model.Booking;
import com.bzu.project.model.BookingRoom;
import com.bzu.project.model.Room;
import com.bzu.project.service.BookingRoomService;
import com.bzu.project.service.BookingService;
import com.bzu.project.service.RoomService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping(value = "/api/v1/booking-rooms", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class BookingRoomController {

    private final BookingRoomService bookingRoomService;
    private final BookingRoomAssembler bookingRoomAssembler;
    private final BookingService bookingService;
    private final RoomService roomService;

    @GetMapping
    public CollectionModel<EntityModel<BookingRoomDTO>> getAllBookingRooms() {
        List<BookingRoomDTO> bookingRooms = bookingRoomService.getAllBookingRooms();
        return bookingRoomAssembler.toCollectionModel(bookingRooms.stream()
                        .map(this::convertToEntity)
                        .collect(Collectors.toList()))
                .add(linkTo(methodOn(BookingRoomController.class).getAllBookingRooms()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<BookingRoomDTO> getBookingRoomById(@PathVariable Long id) {
        BookingRoomDTO bookingRoomDTO = bookingRoomService.getBookingRoomById(id);
        return bookingRoomAssembler.toModel(convertToEntity(bookingRoomDTO));
    }

    @PostMapping
    public ResponseEntity<EntityModel<BookingRoomDTO>> createBookingRoom(@RequestBody BookingRoomDTO bookingRoomDTO) {
        BookingRoomDTO createdBookingRoomDTO = bookingRoomService.createBookingRoom(bookingRoomDTO);
        return ResponseEntity
                .created(linkTo(methodOn(BookingRoomController.class).getBookingRoomById(createdBookingRoomDTO.getId())).toUri())
                .body(bookingRoomAssembler.toModel(convertToEntity(createdBookingRoomDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<BookingRoomDTO>> updateBookingRoom(@PathVariable Long id, @RequestBody BookingRoomDTO bookingRoomDTO) {
        BookingRoomDTO updatedBookingRoomDTO = bookingRoomService.updateBookingRoom(id, bookingRoomDTO);
        return ResponseEntity.ok(bookingRoomAssembler.toModel(convertToEntity(updatedBookingRoomDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookingRoom(@PathVariable Long id) {
        bookingRoomService.deleteBookingRoom(id);
        return ResponseEntity.noContent().build();
    }

    private BookingRoom convertToEntity(BookingRoomDTO bookingRoomDTO) {
        BookingRoom bookingRoom = new BookingRoom();
        bookingRoom.setId(bookingRoomDTO.getId());
        // Fetch Booking and Room entities using service methods
        Booking booking = bookingService.getBookingById(bookingRoomDTO.getBookingId());
        Room room = roomService.getRoomById(bookingRoomDTO.getRoomId());
        bookingRoom.setBooking(booking);
        bookingRoom.setRoom(room);
        return bookingRoom;
    }
}
