package com.bzu.project.controllers;

import com.bzu.project.dto.RoomStatusDTO;
import com.bzu.project.service.RoomStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-statuses")
public class RoomStatusController {

    @Autowired
    private RoomStatusService roomStatusService;

    @GetMapping
    public List<RoomStatusDTO> getAllRoomStatuses() {
        return roomStatusService.getAllRoomStatuses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomStatusDTO> getRoomStatusById(@PathVariable Long id) {
        var roomStatusDTO = roomStatusService.getRoomStatusById(id);
        return roomStatusDTO != null ? ResponseEntity.ok(roomStatusDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public RoomStatusDTO createRoomStatus(@RequestBody RoomStatusDTO roomStatusDTO) {
        return roomStatusService.createRoomStatus(roomStatusDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomStatusDTO> updateRoomStatus(@PathVariable Long id, @RequestBody RoomStatusDTO roomStatusDTO) {
        RoomStatusDTO updatedRoomStatus = roomStatusService.updateRoomStatus(id, roomStatusDTO);
        return updatedRoomStatus != null ? ResponseEntity.ok(updatedRoomStatus) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomStatus(@PathVariable Long id) {
        roomStatusService.deleteRoomStatus(id);
        return ResponseEntity.noContent().build();
    }
}