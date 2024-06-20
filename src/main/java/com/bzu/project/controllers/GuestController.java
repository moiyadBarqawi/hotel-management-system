package com.bzu.project.controllers;

import com.bzu.project.assembler.GuestModelAssembler;
import com.bzu.project.dto.GuestDTO;
import com.bzu.project.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @GetMapping("/{id}")
    public ResponseEntity<GuestDTO> getGuestById(@PathVariable Long id) {
        GuestDTO guestDTO = guestService.getGuestById(id);
        return ResponseEntity.ok(GuestModelAssembler.toModel(guestDTO));
    }

    @GetMapping
    public ResponseEntity<CollectionModel<? extends GuestDTO>> getAllGuests() {
        List<GuestDTO> guests = guestService.getAllGuests();
        return ResponseEntity.ok(GuestModelAssembler.toCollectionModel(guests));
    }

    @PostMapping
    public GuestDTO createGuest(@RequestBody GuestDTO guestDTO) {
        return guestService.createGuest(guestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuestDTO> updateGuest(@PathVariable Long id, @RequestBody GuestDTO guestDTO) {
        GuestDTO updatedGuest = guestService.updateGuest(id, guestDTO);
        return updatedGuest != null ? ResponseEntity.ok(updatedGuest) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long id) {
        guestService.deleteGuest(id);
        return ResponseEntity.noContent().build();
    }
}
