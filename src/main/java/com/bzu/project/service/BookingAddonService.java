package com.bzu.project.service;

import com.bzu.project.dto.BookingAddonDTO;
import com.bzu.project.model.Booking;
import com.bzu.project.model.BookingAddon;
import com.bzu.project.model.Addon;
import com.bzu.project.repository.AddonRepository;
import com.bzu.project.repository.BookingAddonRepository;
import com.bzu.project.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingAddonService {

    @Autowired
    private BookingAddonRepository bookingAddonRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private AddonRepository addonRepository;

    public List<BookingAddonDTO> getAllBookingAddons() {
        return bookingAddonRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BookingAddonDTO getBookingAddonById(Long bookingId, Long addonId) {
        Optional<BookingAddon> bookingAddon = bookingAddonRepository.findByBookingIdAndAddonId(bookingId, addonId);
        return bookingAddon.map(this::convertToDTO).orElse(null);
    }

    public BookingAddonDTO createBookingAddon(BookingAddonDTO bookingAddonDTO) {
        BookingAddon bookingAddon = convertToEntity(bookingAddonDTO);
        return convertToDTO(bookingAddonRepository.save(bookingAddon));
    }

    public BookingAddonDTO updateBookingAddon(Long bookingId, Long addonId, BookingAddonDTO bookingAddonDTO) {
        Optional<BookingAddon> existingBookingAddon = bookingAddonRepository.findByBookingIdAndAddonId(bookingId, addonId);
        if (existingBookingAddon.isPresent()) {
            BookingAddon bookingAddon = existingBookingAddon.get();
            bookingAddon.setBooking(bookingRepository.findById(bookingAddonDTO.getBookingId()).orElse(null));
            bookingAddon.setAddon(addonRepository.findById(bookingAddonDTO.getAddonId()).orElse(null));
            return convertToDTO(bookingAddonRepository.save(bookingAddon));
        } else {
            return null;
        }
    }

    public void deleteBookingAddon(Long bookingId, Long addonId) {
        bookingAddonRepository.deleteByBookingIdAndAddonId(bookingId, addonId);
    }

    private BookingAddon convertToEntity(BookingAddonDTO bookingAddonDTO) {
        BookingAddon bookingAddon = new BookingAddon();
        bookingAddon.setBooking(bookingRepository.findById(bookingAddonDTO.getBookingId()).orElse(null));
        bookingAddon.setAddon(addonRepository.findById(bookingAddonDTO.getAddonId()).orElse(null));
        return bookingAddon;
    }

    private BookingAddonDTO convertToDTO(BookingAddon bookingAddon) {
        BookingAddonDTO bookingAddonDTO = new BookingAddonDTO();
        bookingAddonDTO.setId(bookingAddon.getId());
        bookingAddonDTO.setBookingId(bookingAddon.getBooking().getId());
        bookingAddonDTO.setAddonId(bookingAddon.getAddon().getId());
        return bookingAddonDTO;
    }
}
