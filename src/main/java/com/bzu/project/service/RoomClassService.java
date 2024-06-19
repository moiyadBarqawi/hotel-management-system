package com.bzu.project.service;


import com.bzu.project.dto.RoomClassDTO;
import com.bzu.project.model.RoomClass;
import com.bzu.project.repository.RoomClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomClassService {

    @Autowired
    private RoomClassRepository roomClassRepository;

    public List<RoomClassDTO> getAllRoomClasses() {
        return roomClassRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public RoomClassDTO getRoomClassById(Long id) {
        Optional<RoomClass> roomClass = roomClassRepository.findById(id);
        return roomClass.map(this::convertToDTO).orElse(null);
    }

    public RoomClassDTO createRoomClass(RoomClassDTO roomClassDTO) {
        RoomClass roomClass = convertToEntity(roomClassDTO);
        return convertToDTO(roomClassRepository.save(roomClass));
    }

    public RoomClassDTO updateRoomClass(Long id, RoomClassDTO roomClassDTO) {
        Optional<RoomClass> existingRoomClass = roomClassRepository.findById(id);
        if (existingRoomClass.isPresent()) {
            RoomClass roomClass = existingRoomClass.get();
            roomClass.setClassName(roomClassDTO.getClassName());
            roomClass.setBasePrice(roomClassDTO.getBasePrice());
            return convertToDTO(roomClassRepository.save(roomClass));
        } else {
            return null;
        }
    }

    public void deleteRoomClass(Long id) {
        roomClassRepository.deleteById(id);
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