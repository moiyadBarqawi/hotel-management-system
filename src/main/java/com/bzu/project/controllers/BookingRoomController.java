package com.bzu.project.controllers;

import com.bzu.project.dto.BookingRoomDTO;
import com.bzu.project.service.BookingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking-rooms")
public class BookingRoomController {

    @Autowired
    private BookingRoomService bookingRoomService;

    @GetMapping
    public List<BookingRoomDTO> getAllBookingRooms() {
        return bookingRoomService.getAllBookingRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingRoomDTO> getBookingRoomById(@PathVariable Long id) {
        BookingRoomDTO bookingRoomDTO = bookingRoomService.getBookingRoomById(id);
        return bookingRoomDTO != null ? ResponseEntity.ok(bookingRoomDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<BookingRoomDTO> createBookingRoom(@RequestBody BookingRoomDTO bookingRoomDTO) {
        BookingRoomDTO createdBookingRoom = bookingRoomService.createBookingRoom(bookingRoomDTO);
        return ResponseEntity.ok(createdBookingRoom);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingRoomDTO> updateBookingRoom(@PathVariable Long id, @RequestBody BookingRoomDTO bookingRoomDTO) {
        BookingRoomDTO updatedBookingRoom = bookingRoomService.updateBookingRoom(id, bookingRoomDTO);
        return updatedBookingRoom != null ? ResponseEntity.ok(updatedBookingRoom) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookingRoom(@PathVariable Long id) {
        bookingRoomService.deleteBookingRoom(id);
        return ResponseEntity.noContent().build();
    }
}
