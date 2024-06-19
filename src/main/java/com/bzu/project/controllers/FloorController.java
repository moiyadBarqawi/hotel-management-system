package com.bzu.project.controllers;


import com.bzu.project.dto.FloorDTO;
import com.bzu.project.service.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/floors")
public class FloorController {

    @Autowired
    private FloorService floorService;

    @GetMapping
    public List<FloorDTO> getAllFloors() {
        return floorService.getAllFloors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FloorDTO> getFloorById(@PathVariable Long id) {
        FloorDTO floorDTO = floorService.getFloorById(id);
        return floorDTO != null ? ResponseEntity.ok(floorDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public FloorDTO createFloor(@RequestBody FloorDTO floorDTO) {
        return floorService.createFloor(floorDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FloorDTO> updateFloor(@PathVariable Long id, @RequestBody FloorDTO floorDTO) {
        FloorDTO updatedFloor = floorService.updateFloor(id, floorDTO);
        return updatedFloor != null ? ResponseEntity.ok(updatedFloor) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFloor(@PathVariable Long id) {
        floorService.deleteFloor(id);
        return ResponseEntity.noContent().build();
    }
}