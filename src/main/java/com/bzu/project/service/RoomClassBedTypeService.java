package com.bzu.project.service;

import com.bzu.project.dto.RoomClassBedTypeDTO;
import com.bzu.project.exception.ResourceNotFoundException;
import com.bzu.project.model.BedType;
import com.bzu.project.model.RoomClass;
import com.bzu.project.model.RoomClassBedType;
import com.bzu.project.repository.BedTypeRepository;
import com.bzu.project.repository.RoomClassBedTypeRepository;
import com.bzu.project.repository.RoomClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomClassBedTypeService {

    @Autowired
    private RoomClassBedTypeRepository roomClassBedTypeRepository;

    @Autowired
    private RoomClassRepository roomClassRepository;

    @Autowired
    private BedTypeRepository bedTypeRepository;

    public List<RoomClassBedTypeDTO> getAllRoomClassBedTypes() {
        return roomClassBedTypeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RoomClassBedTypeDTO getRoomClassBedTypeById(Long roomClassId, Long bedTypeId) {
        RoomClassBedType roomClassBedType = roomClassBedTypeRepository.findByRoomClassIdAndBedTypeId(roomClassId, bedTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Room class bed type not found with roomClassId " + roomClassId + " and bedTypeId " + bedTypeId));
        return convertToDTO(roomClassBedType);
    }

    public RoomClassBedTypeDTO createRoomClassBedType(RoomClassBedTypeDTO roomClassBedTypeDTO) {
        RoomClassBedType roomClassBedType = convertToEntity(roomClassBedTypeDTO);
        return convertToDTO(roomClassBedTypeRepository.save(roomClassBedType));
    }

    public RoomClassBedTypeDTO updateRoomClassBedType(Long roomClassId, Long bedTypeId, RoomClassBedTypeDTO roomClassBedTypeDTO) {
        RoomClassBedType roomClassBedType = roomClassBedTypeRepository.findByRoomClassIdAndBedTypeId(roomClassId, bedTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Room class bed type not found with roomClassId " + roomClassId + " and bedTypeId " + bedTypeId));
        roomClassBedType.setRoomClass(roomClassRepository.findById(roomClassBedTypeDTO.getRoomClassId()).orElse(null));
        roomClassBedType.setBedType(bedTypeRepository.findById(roomClassBedTypeDTO.getBedTypeId()).orElse(null));
        roomClassBedType.setNumBeds(roomClassBedTypeDTO.getNumBeds());
        return convertToDTO(roomClassBedTypeRepository.save(roomClassBedType));
    }

    public void deleteRoomClassBedType(Long roomClassId, Long bedTypeId) {
        RoomClassBedType roomClassBedType = roomClassBedTypeRepository.findByRoomClassIdAndBedTypeId(roomClassId, bedTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Room class bed type not found with roomClassId " + roomClassId + " and bedTypeId " + bedTypeId));
        roomClassBedTypeRepository.delete(roomClassBedType);
    }

    public RoomClassBedType convertToEntity(RoomClassBedTypeDTO roomClassBedTypeDTO) {
        RoomClassBedType roomClassBedType = new RoomClassBedType();
        roomClassBedType.setRoomClass(roomClassRepository.findById(roomClassBedTypeDTO.getRoomClassId()).orElse(null));
        roomClassBedType.setBedType(bedTypeRepository.findById(roomClassBedTypeDTO.getBedTypeId()).orElse(null));
        roomClassBedType.setNumBeds(roomClassBedTypeDTO.getNumBeds());
        return roomClassBedType;
    }

    private RoomClassBedTypeDTO convertToDTO(RoomClassBedType roomClassBedType) {
        RoomClassBedTypeDTO roomClassBedTypeDTO = new RoomClassBedTypeDTO();
        roomClassBedTypeDTO.setRoomClassId(roomClassBedType.getRoomClass().getId());
        roomClassBedTypeDTO.setBedTypeId(roomClassBedType.getBedType().getId());
        roomClassBedTypeDTO.setNumBeds(roomClassBedType.getNumBeds());
        return roomClassBedTypeDTO;
    }
}
