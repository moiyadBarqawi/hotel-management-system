package com.bzu.project.service;

import com.bzu.project.dto.BookingRoomDTO;
import com.bzu.project.exception.ResourceNotFoundException;
import com.bzu.project.model.BookingRoom;
import com.bzu.project.model.Booking;
import com.bzu.project.model.Room;
import com.bzu.project.repository.BookingRoomRepository;
import com.bzu.project.repository.BookingRepository;
import com.bzu.project.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BookingRoomService {

    @Autowired
    private BookingRoomRepository bookingRoomRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RoomRepository roomRepository;

    public Page<BookingRoom> getAllBookingRooms(PageRequest pageRequest) {
        return bookingRoomRepository.findAll(pageRequest);
    }

    public BookingRoom getBookingRoomById(Long id) {
        return bookingRoomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BookingRoom not found with id " + id));
    }

    public BookingRoom createBookingRoom(BookingRoomDTO bookingRoomDTO) {
        BookingRoom bookingRoom = convertToEntity(bookingRoomDTO);
        return bookingRoomRepository.save(bookingRoom);
    }

    public BookingRoom updateBookingRoom(Long id, BookingRoomDTO bookingRoomDTO) {
        BookingRoom bookingRoom = bookingRoomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BookingRoom not found with id " + id));

        Booking booking = bookingRepository.findById(bookingRoomDTO.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + bookingRoomDTO.getBookingId()));
        Room room = roomRepository.findById(bookingRoomDTO.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + bookingRoomDTO.getRoomId()));

        bookingRoom.setBooking(booking);
        bookingRoom.setRoom(room);

        return bookingRoomRepository.save(bookingRoom);
    }

    public void deleteBookingRoom(Long id) {
        bookingRoomRepository.deleteById(id);
    }

    private BookingRoom convertToEntity(BookingRoomDTO bookingRoomDTO) {
        BookingRoom bookingRoom = new BookingRoom();
        bookingRoom.setId(bookingRoomDTO.getId());
        bookingRoom.setBooking(bookingRepository.findById(bookingRoomDTO.getBookingId()).orElse(null));
        bookingRoom.setRoom(roomRepository.findById(bookingRoomDTO.getRoomId()).orElse(null));
        return bookingRoom;
    }
}
