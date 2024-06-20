package com.bzu.project.service;

import com.bzu.project.dto.BookingDTO;
import com.bzu.project.model.Booking;
import com.bzu.project.repository.BookingRepository;
import com.bzu.project.repository.GuestRepository;
import com.bzu.project.repository.PaymentStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public BookingDTO getBookingById(Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        return booking.map(this::convertToDTO).orElse(null);
    }

    public BookingDTO createBooking(BookingDTO bookingDTO) {
        Booking booking = convertToEntity(bookingDTO);
        return convertToDTO(bookingRepository.save(booking));
    }

    public BookingDTO updateBooking(Long id, BookingDTO bookingDTO) {
        Optional<Booking> existingBooking = bookingRepository.findById(id);
        if (existingBooking.isPresent()) {
            Booking booking = existingBooking.get();
            booking.setGuest(guestRepository.findById(bookingDTO.getGuestId()).orElse(null));
            booking.setPaymentStatus(paymentStatusRepository.findById(bookingDTO.getPaymentStatusId()).orElse(null));
            booking.setCheckinDate(bookingDTO.getCheckinDate());
            booking.setCheckoutDate(bookingDTO.getCheckoutDate());
            booking.setNumAdults(bookingDTO.getNumAdults());
            booking.setNumChildren(bookingDTO.getNumChildren());
            booking.setBookingAmount(bookingDTO.getBookingAmount());
            return convertToDTO(bookingRepository.save(booking));
        } else {
            return null;
        }
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    private Booking convertToEntity(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setId(bookingDTO.getId());
        booking.setGuest(guestRepository.findById(bookingDTO.getGuestId()).orElse(null));
        booking.setPaymentStatus(paymentStatusRepository.findById(bookingDTO.getPaymentStatusId()).orElse(null));
        booking.setCheckinDate(bookingDTO.getCheckinDate());
        booking.setCheckoutDate(bookingDTO.getCheckoutDate());
        booking.setNumAdults(bookingDTO.getNumAdults());
        booking.setNumChildren(bookingDTO.getNumChildren());
        booking.setBookingAmount(bookingDTO.getBookingAmount());
        return booking;
    }

    private BookingDTO convertToDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setGuestId(booking.getGuest().getId());
        bookingDTO.setPaymentStatusId(booking.getPaymentStatus().getId());
        bookingDTO.setCheckinDate(booking.getCheckinDate());
        bookingDTO.setCheckoutDate(booking.getCheckoutDate());
        bookingDTO.setNumAdults(booking.getNumAdults());
        bookingDTO.setNumChildren(booking.getNumChildren());
        bookingDTO.setBookingAmount(booking.getBookingAmount());
        return bookingDTO;
    }
}

