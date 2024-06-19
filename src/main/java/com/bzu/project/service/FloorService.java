package com.bzu.project.service;

import com.bzu.project.dto.FloorDTO;
import com.bzu.project.model.Floor;
import com.bzu.project.repository.FloorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FloorService {

    @Autowired
    private FloorRepository floorRepository;

    public List<FloorDTO> getAllFloors() {
        return floorRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public FloorDTO getFloorById(Long id) {
        Optional<Floor> floor = floorRepository.findById(id);
        return floor.map(this::convertToDTO).orElse(null);
    }

    public FloorDTO createFloor(FloorDTO floorDTO) {
        Floor floor = convertToEntity(floorDTO);
        return convertToDTO(floorRepository.save(floor));
    }

    public FloorDTO updateFloor(Long id, FloorDTO floorDTO) {
        Optional<Floor> existingFloor = floorRepository.findById(id);
        if (existingFloor.isPresent()) {
            Floor floor = existingFloor.get();
            floor.setFloorNumber(floorDTO.getFloorNumber());
            return convertToDTO(floorRepository.save(floor));
        } else {
            return null;
        }
    }

    public void deleteFloor(Long id) {
        floorRepository.deleteById(id);
    }

    private Floor convertToEntity(FloorDTO floorDTO) {
        Floor floor = new Floor();
        floor.setId(floorDTO.getId());
        floor.setFloorNumber(floorDTO.getFloorNumber());
        return floor;
    }

    private FloorDTO convertToDTO(Floor floor) {
        FloorDTO floorDTO = new FloorDTO();
        floorDTO.setId(floor.getId());
        floorDTO.setFloorNumber(floor.getFloorNumber());
        return floorDTO;
    }
}