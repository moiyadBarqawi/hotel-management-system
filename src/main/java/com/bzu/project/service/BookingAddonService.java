package com.bzu.project.service;

import com.bzu.project.dto.BookingAddonDTO;
import com.bzu.project.exception.ResourceNotFoundException;
import com.bzu.project.model.BookingAddon;
import com.bzu.project.repository.AddonRepository;
import com.bzu.project.repository.BookingAddonRepository;
import com.bzu.project.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingAddonService {

    @Autowired
    private BookingAddonRepository bookingAddonRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private AddonRepository addonRepository;

    public Page<BookingAddonDTO> getAllBookingAddons(PageRequest pageRequest) {
        Page<BookingAddon> bookingAddons = bookingAddonRepository.findAll(pageRequest);
        return bookingAddons.map(this::convertToDTO);
    }

    public BookingAddonDTO getBookingAddonById(Long bookingId, Long addonId) {
        BookingAddon bookingAddon = bookingAddonRepository.findByBookingIdAndAddonId(bookingId, addonId)
                .orElseThrow(() -> new ResourceNotFoundException("BookingAddon not found with bookingId " + bookingId + " and addonId " + addonId));
        return convertToDTO(bookingAddon);
    }

    public BookingAddonDTO createBookingAddon(BookingAddonDTO bookingAddonDTO) {
        BookingAddon bookingAddon = convertToEntity(bookingAddonDTO);
        BookingAddon savedBookingAddon = bookingAddonRepository.save(bookingAddon);
        return convertToDTO(savedBookingAddon);
    }

    public BookingAddonDTO updateBookingAddon(Long bookingId, Long addonId, BookingAddonDTO bookingAddonDTO) {
        BookingAddon bookingAddon = bookingAddonRepository.findByBookingIdAndAddonId(bookingId, addonId)
                .orElseThrow(() -> new ResourceNotFoundException("BookingAddon not found with bookingId " + bookingId + " and addonId " + addonId));
        bookingAddon.setBooking(bookingRepository.findById(bookingAddonDTO.getBookingId()).orElse(null));
        bookingAddon.setAddon(addonRepository.findById(bookingAddonDTO.getAddonId()).orElse(null));
        BookingAddon updatedBookingAddon = bookingAddonRepository.save(bookingAddon);
        return convertToDTO(updatedBookingAddon);
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
