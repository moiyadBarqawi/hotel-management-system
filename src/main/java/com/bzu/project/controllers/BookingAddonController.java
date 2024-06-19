package com.bzu.project.controllers;

import com.bzu.project.dto.BookingAddonDTO;
import com.bzu.project.service.BookingAddonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking-addons")
public class BookingAddonController {

    @Autowired
    private BookingAddonService bookingAddonService;

    @GetMapping
    public List<BookingAddonDTO> getAllBookingAddons() {
        return bookingAddonService.getAllBookingAddons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingAddonDTO> getBookingAddonById(@PathVariable Long id) {
        BookingAddonDTO bookingAddonDTO = bookingAddonService.getBookingAddonById(id);
        return bookingAddonDTO != null ? ResponseEntity.ok(bookingAddonDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<BookingAddonDTO> createBookingAddon(@RequestBody BookingAddonDTO bookingAddonDTO) {
        BookingAddonDTO createdBookingAddon = bookingAddonService.createBookingAddon(bookingAddonDTO);
        return ResponseEntity.ok(createdBookingAddon);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingAddonDTO> updateBookingAddon(@PathVariable Long id, @RequestBody BookingAddonDTO bookingAddonDTO) {
        BookingAddonDTO updatedBookingAddon = bookingAddonService.updateBookingAddon(id, bookingAddonDTO);
        return updatedBookingAddon != null ? ResponseEntity.ok(updatedBookingAddon) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookingAddon(@PathVariable Long id) {
        bookingAddonService.deleteBookingAddon(id);
        return ResponseEntity.noContent().build();
    }
}
