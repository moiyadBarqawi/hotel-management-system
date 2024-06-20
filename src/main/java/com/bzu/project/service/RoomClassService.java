package com.bzu.project.service;

import com.bzu.project.dto.RoomClassDTO;
import com.bzu.project.exception.ResourceNotFoundException;
import com.bzu.project.model.RoomClass;
import com.bzu.project.repository.RoomClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomClassService {

    @Autowired
    private RoomClassRepository roomClassRepository;

    public Page<RoomClass> getAllRoomClasses(PageRequest pageRequest) {
        return roomClassRepository.findAll(pageRequest);
    }

    public RoomClass getRoomClassById(Long id) {
        return roomClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room class not found with id " + id));
    }

    public RoomClass createRoomClass(RoomClassDTO roomClassDTO) {
        RoomClass roomClass = convertToEntity(roomClassDTO);
        return roomClassRepository.save(roomClass);
    }

    public RoomClass updateRoomClass(Long id, RoomClassDTO roomClassDTO) {
        RoomClass roomClass = roomClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room class not found with id " + id));
        roomClass.setClassName(roomClassDTO.getClassName());
        roomClass.setBasePrice(roomClassDTO.getBasePrice());
        return roomClassRepository.save(roomClass);
    }

    public void deleteRoomClass(Long id) {
        RoomClass roomClass = roomClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room class not found with id " + id));
        roomClassRepository.delete(roomClass);
    }

    private RoomClass convertToEntity(RoomClassDTO roomClassDTO) {
        RoomClass roomClass = new RoomClass();
        roomClass.setId(roomClassDTO.getId());
        roomClass.setClassName(roomClassDTO.getClassName());
        roomClass.setBasePrice(roomClassDTO.getBasePrice());
        return roomClass;
    }

    private RoomClassDTO convertToDTO(RoomClass roomClass) {
        RoomClassDTO roomClassDTO = new RoomClassDTO();
        roomClassDTO.setId(roomClass.getId());
        roomClassDTO.setClassName(roomClass.getClassName());
        roomClassDTO.setBasePrice(roomClass.getBasePrice());
        return roomClassDTO;
    }
}
