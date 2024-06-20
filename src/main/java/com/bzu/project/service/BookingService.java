package com.bzu.project.service;

import com.bzu.project.dto.BookingDTO;
import com.bzu.project.exception.ResourceNotFoundException;
import com.bzu.project.model.Booking;
import com.bzu.project.repository.BookingRepository;
import com.bzu.project.repository.GuestRepository;
import com.bzu.project.repository.PaymentStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    public Page<BookingDTO> getAllBookings(PageRequest pageRequest) {
        Page<Booking> bookings = bookingRepository.findAll(pageRequest);
        return bookings.map(this::convertToDTO);
    }

    public BookingDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + id));
        return convertToDTO(booking);
    }

    public Booking getBookingByIdAsEntity(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + id));
    }

    public BookingDTO createBooking(BookingDTO bookingDTO) {
        Booking booking = convertToEntity(bookingDTO);
        return convertToDTO(bookingRepository.save(booking));
    }

    public BookingDTO updateBooking(Long id, BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + id));

        booking.setGuest(guestRepository.findById(bookingDTO.getGuestId()).orElse(null));
        booking.setPaymentStatus(paymentStatusRepository.findById(bookingDTO.getPaymentStatusId()).orElse(null));
        booking.setCheckinDate(bookingDTO.getCheckinDate());
        booking.setCheckoutDate(bookingDTO.getCheckoutDate());
        booking.setNumAdults(bookingDTO.getNumAdults());
        booking.setNumChildren(bookingDTO.getNumChildren());
        booking.setBookingAmount(bookingDTO.getBookingAmount());

        return convertToDTO(bookingRepository.save(booking));
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
