package com.bzu.project.controllers;

import com.bzu.project.assembler.BookingRoomAssembler;
import com.bzu.project.dto.BookingRoomDTO;
import com.bzu.project.model.BookingRoom;
import com.bzu.project.service.BookingRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/v1/booking-rooms", produces = MediaTypes.HAL_JSON_VALUE)
@OpenAPIDefinition(
        info = @Info(
                title = "Booking Rooms API",
                version = "1.0",
                description = "API for managing booking rooms",
                contact = @Contact(
                        name = "Support",
                        email = "support@example.com"
                )
        )
)
@RequiredArgsConstructor
public class BookingRoomController {

    private final BookingRoomService bookingRoomService;
    private final BookingRoomAssembler bookingRoomAssembler;

    @Operation(summary = "Get all booking rooms", description = "Retrieve a list of all booking rooms")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @GetMapping
    public CollectionModel<EntityModel<BookingRoomDTO>> getAllBookingRooms(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                           @RequestParam(name = "size", defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<BookingRoom> bookingRoomPage = bookingRoomService.getAllBookingRooms(pageRequest);
        List<EntityModel<BookingRoomDTO>> bookingRooms = bookingRoomPage.getContent().stream()
                .map(bookingRoomAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(bookingRooms, linkTo(methodOn(BookingRoomController.class).getAllBookingRooms(page, size)).withSelfRel());
    }

    @Operation(summary = "Get booking room by ID", description = "Retrieve a booking room by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved booking room"),
            @ApiResponse(responseCode = "404", description = "Booking room not found")
    })
    @GetMapping("/{id}")
    public EntityModel<BookingRoomDTO> getBookingRoomById(@PathVariable Long id) {
        BookingRoom bookingRoom = bookingRoomService.getBookingRoomById(id);
        return bookingRoomAssembler.toModel(bookingRoom);
    }

    @Operation(summary = "Create a new booking room", description = "Add a new booking room to the system")
    @ApiResponse(responseCode = "201", description = "Booking room created successfully")
    @PostMapping
    public ResponseEntity<EntityModel<BookingRoomDTO>> createBookingRoom(@RequestBody BookingRoomDTO bookingRoomDTO) {
        BookingRoom createdBookingRoom = bookingRoomService.createBookingRoom(bookingRoomDTO);
        return ResponseEntity
                .created(linkTo(methodOn(BookingRoomController.class).getBookingRoomById(createdBookingRoom.getId())).toUri())
                .body(bookingRoomAssembler.toModel(createdBookingRoom));
    }

    @Operation(summary = "Update a booking room", description = "Update details of an existing booking room")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Booking room updated successfully"),
            @ApiResponse(responseCode = "404", description = "Booking room not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<BookingRoomDTO>> updateBookingRoom(@PathVariable Long id, @RequestBody BookingRoomDTO bookingRoomDTO) {
        BookingRoom updatedBookingRoom = bookingRoomService.updateBookingRoom(id, bookingRoomDTO);
        return ResponseEntity.ok(bookingRoomAssembler.toModel(updatedBookingRoom));
    }

    @Operation(summary = "Delete a booking room", description = "Delete a booking room from the system")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Booking room deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Booking room not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookingRoom(@PathVariable Long id) {
        bookingRoomService.deleteBookingRoom(id);
        return ResponseEntity.noContent().build();
    }
}
