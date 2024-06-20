package com.bzu.project.service;

import com.bzu.project.dto.FloorDTO;
import com.bzu.project.exception.ResourceNotFoundException;
import com.bzu.project.model.Floor;
import com.bzu.project.repository.FloorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class FloorService {
    @Autowired
    private FloorRepository floorRepository;

    public Page<Floor> getAllFloors(PageRequest pageRequest) {
        return floorRepository.findAll(pageRequest);
    }

    public FloorDTO getFloorById(Long id) {
        Floor floor = floorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Floor not found with id " + id));
        return convertToDTO(floor);
    }

    public FloorDTO createFloor(FloorDTO floorDTO) {
        Floor floor = convertToEntity(floorDTO);
        return convertToDTO(floorRepository.save(floor));
    }

    public FloorDTO updateFloor(Long id, FloorDTO floorDTO) {
        Floor floor = floorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Floor not found with id " + id));
        floor.setFloorNumber(floorDTO.getFloorNumber());
        return convertToDTO(floorRepository.save(floor));
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
