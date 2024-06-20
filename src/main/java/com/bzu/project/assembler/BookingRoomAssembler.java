package com.bzu.project.assembler;

import com.bzu.project.controllers.BookingRoomController;
import com.bzu.project.dto.BookingRoomDTO;
import com.bzu.project.model.BookingRoom;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookingRoomAssembler extends RepresentationModelAssemblerSupport<BookingRoom, EntityModel<BookingRoomDTO>> {

    public BookingRoomAssembler() {
        super(BookingRoomController.class, (Class<EntityModel<BookingRoomDTO>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<BookingRoomDTO> toModel(BookingRoom bookingRoom) {
        BookingRoomDTO dto = convertToDTO(bookingRoom);
        return EntityModel.of(dto,
                linkTo(methodOn(BookingRoomController.class).getBookingRoomById(bookingRoom.getId())).withSelfRel(),
                linkTo(methodOn(BookingRoomController.class).getAllBookingRooms(0, 10)).withRel("booking-rooms"));
    }

    @Override
    public CollectionModel<EntityModel<BookingRoomDTO>> toCollectionModel(Iterable<? extends BookingRoom> entities) {
        List<EntityModel<BookingRoomDTO>> dtos = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(dtos,
                linkTo(methodOn(BookingRoomController.class).getAllBookingRooms(0, 10)).withSelfRel());
    }

    private BookingRoomDTO convertToDTO(BookingRoom bookingRoom) {
        BookingRoomDTO dto = new BookingRoomDTO();
        dto.setId(bookingRoom.getId());
        dto.setBookingId(bookingRoom.getBooking().getId());
        dto.setRoomId(bookingRoom.getRoom().getId());
        return dto;
    }
}
