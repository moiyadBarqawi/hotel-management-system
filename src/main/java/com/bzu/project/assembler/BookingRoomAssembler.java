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
        BookingRoomDTO bookingRoomDTO = convertToDTO(bookingRoom);
        return EntityModel.of(bookingRoomDTO,
                linkTo(methodOn(BookingRoomController.class).getBookingRoomById(bookingRoom.getId())).withSelfRel(),
                linkTo(methodOn(BookingRoomController.class).getAllBookingRooms()).withRel("booking-rooms"));
    }

    @Override
    public CollectionModel<EntityModel<BookingRoomDTO>> toCollectionModel(Iterable<? extends BookingRoom> entities) {
        List<EntityModel<BookingRoomDTO>> bookingRoomDTOs = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(bookingRoomDTOs,
                linkTo(methodOn(BookingRoomController.class).getAllBookingRooms()).withSelfRel());
    }

    private BookingRoomDTO convertToDTO(BookingRoom bookingRoom) {
        BookingRoomDTO bookingRoomDTO = new BookingRoomDTO();
        bookingRoomDTO.setId(bookingRoom.getId());
        bookingRoomDTO.setBookingId(bookingRoom.getBooking().getId());
        bookingRoomDTO.setRoomId(bookingRoom.getRoom().getId());
        return bookingRoomDTO;
    }
}
