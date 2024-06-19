package com.bzu.project.controllers;


import com.bzu.project.dto.RoomClassDTO;
import com.bzu.project.service.RoomClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-classes")
public class RoomClassController {

    @Autowired
    private RoomClassService roomClassService;

    @GetMapping
    public List<RoomClassDTO> getAllRoomClasses() {
        return roomClassService.getAllRoomClasses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomClassDTO> getRoomClassById(@PathVariable Long id) {
        RoomClassDTO roomClassDTO = roomClassService.getRoomClassById(id);
        return roomClassDTO != null ? ResponseEntity.ok(roomClassDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public RoomClassDTO createRoomClass(@RequestBody RoomClassDTO roomClassDTO) {
        return roomClassService.createRoomClass(roomClassDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomClassDTO> updateRoomClass(@PathVariable Long id, @RequestBody RoomClassDTO roomClassDTO) {
        RoomClassDTO updatedRoomClass = roomClassService.updateRoomClass(id, roomClassDTO);
        return updatedRoomClass != null ? ResponseEntity.ok(updatedRoomClass) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomClass(@PathVariable Long id) {
        roomClassService.deleteRoomClass(id);
        return ResponseEntity.noContent().build();
    }
}
