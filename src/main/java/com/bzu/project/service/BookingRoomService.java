package com.bzu.project.service;

import com.bzu.project.dto.BookingRoomDTO;
import com.bzu.project.model.Booking;
import com.bzu.project.model.BookingRoom;
import com.bzu.project.model.Room;
import com.bzu.project.repository.BookingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingRoomService {

    @Autowired
    private BookingRoomRepository bookingRoomRepository;

    public List<BookingRoomDTO> getAllBookingRooms() {
        return bookingRoomRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BookingRoomDTO getBookingRoomById(Long id) {
        Optional<BookingRoom> bookingRoom = bookingRoomRepository.findById(id);
        return bookingRoom.map(this::convertToDTO).orElse(null);
    }

    public BookingRoomDTO createBookingRoom(BookingRoomDTO bookingRoomDTO) {
        Booking booking = new Booking();
        booking.setId(bookingRoomDTO.getBookingId()); // Assuming you have setId method in Booking entity

        Room room = new Room();
        room.setId(bookingRoomDTO.getRoomId()); // Assuming you have setId method in Room entity

        BookingRoom bookingRoom = new BookingRoom();
        bookingRoom.setBooking(booking);
        bookingRoom.setRoom(room);

        return convertToDTO(bookingRoomRepository.save(bookingRoom));
    }

    public BookingRoomDTO updateBookingRoom(Long id, BookingRoomDTO bookingRoomDTO) {
        Optional<BookingRoom> existingBookingRoom = bookingRoomRepository.findById(id);
        if (existingBookingRoom.isPresent()) {
            BookingRoom bookingRoom = existingBookingRoom.get();
            return convertToDTO(bookingRoomRepository.save(bookingRoom));
        } else {
            return null;
        }
    }

    public void deleteBookingRoom(Long id) {
        bookingRoomRepository.deleteById(id);
    }

    private BookingRoomDTO convertToDTO(BookingRoom bookingRoom) {
        BookingRoomDTO bookingRoomDTO = new BookingRoomDTO();
        bookingRoomDTO.setId(bookingRoom.getId());
        bookingRoomDTO.setBookingId(bookingRoom.getBooking().getId());
        bookingRoomDTO.setRoomId(bookingRoom.getRoom().getId());
        return bookingRoomDTO;
    }
}
