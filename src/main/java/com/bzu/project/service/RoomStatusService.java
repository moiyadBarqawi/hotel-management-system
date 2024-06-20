package com.bzu.project.service;

import com.bzu.project.dto.RoomStatusDTO;
import com.bzu.project.model.RoomStatus;
import com.bzu.project.repository.RoomStatusRepository;
import com.bzu.project.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomStatusService {

    @Autowired
    private RoomStatusRepository roomStatusRepository;

    public List<RoomStatusDTO> getAllRoomStatuses() {
        return roomStatusRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public RoomStatusDTO getRoomStatusById(Long id) {
        RoomStatus roomStatus = roomStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room status not found with id " + id));
        return convertToDTO(roomStatus);
    }

    public RoomStatusDTO createRoomStatus(RoomStatusDTO roomStatusDTO) {
        RoomStatus roomStatus = convertToEntity(roomStatusDTO);
        return convertToDTO(roomStatusRepository.save(roomStatus));
    }

    public RoomStatusDTO updateRoomStatus(Long id, RoomStatusDTO roomStatusDTO) {
        RoomStatus roomStatus = roomStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room status not found with id " + id));
        roomStatus.setStatusName(roomStatusDTO.getStatusName());
        return convertToDTO(roomStatusRepository.save(roomStatus));
    }

    public void deleteRoomStatus(Long id) {
        RoomStatus roomStatus = roomStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room status not found with id " + id));
        roomStatusRepository.delete(roomStatus);
    }

    public RoomStatus convertToEntity(RoomStatusDTO roomStatusDTO) {
        RoomStatus roomStatus = new RoomStatus();
        roomStatus.setId(roomStatusDTO.getId());
        roomStatus.setStatusName(roomStatusDTO.getStatusName());
        return roomStatus;
    }

    private RoomStatusDTO convertToDTO(RoomStatus roomStatus) {
        RoomStatusDTO roomStatusDTO = new RoomStatusDTO();
        roomStatusDTO.setId(roomStatus.getId());
        roomStatusDTO.setStatusName(roomStatus.getStatusName());
        return roomStatusDTO;
    }
}
