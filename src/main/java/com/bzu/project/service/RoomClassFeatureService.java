package com.bzu.project.service;

import com.bzu.project.dto.RoomClassFeatureDTO;
import com.bzu.project.model.RoomClassFeature;
import com.bzu.project.repository.RoomClassFeatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomClassFeatureService {

    private final RoomClassFeatureRepository roomClassFeatureRepository;

    public List<RoomClassFeatureDTO> getAllRoomClassFeatures() {
        return roomClassFeatureRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RoomClassFeatureDTO getRoomClassFeatureById(Long roomClassId, Long featureId) {
        RoomClassFeature roomClassFeature = roomClassFeatureRepository.findByRoomClassIdAndFeatureId(roomClassId, featureId)
                .orElseThrow(() -> new RuntimeException("RoomClassFeature not found"));
        return convertToDTO(roomClassFeature);
    }

    public RoomClassFeatureDTO createRoomClassFeature(RoomClassFeatureDTO roomClassFeatureDTO) {
        RoomClassFeature roomClassFeature = convertToEntity(roomClassFeatureDTO);
        roomClassFeature = roomClassFeatureRepository.save(roomClassFeature);
        return convertToDTO(roomClassFeature);
    }

    public RoomClassFeatureDTO updateRoomClassFeature(Long roomClassId, Long featureId, RoomClassFeatureDTO roomClassFeatureDTO) {
        RoomClassFeature existingRoomClassFeature = roomClassFeatureRepository.findByRoomClassIdAndFeatureId(roomClassId, featureId)
                .orElseThrow(() -> new RuntimeException("RoomClassFeature not found"));
        // Update fields as needed
        existingRoomClassFeature.setRoomClass(roomClassFeatureDTO.getRoomClass());
        existingRoomClassFeature.setFeature(roomClassFeatureDTO.getFeature());
        existingRoomClassFeature = roomClassFeatureRepository.save(existingRoomClassFeature);
        return convertToDTO(existingRoomClassFeature);
    }

    public void deleteRoomClassFeature(Long roomClassId, Long featureId) {
        roomClassFeatureRepository.deleteByRoomClassIdAndFeatureId(roomClassId, featureId);
    }

    private RoomClassFeatureDTO convertToDTO(RoomClassFeature roomClassFeature) {
        RoomClassFeatureDTO dto = new RoomClassFeatureDTO();
        dto.setRoomClassId(roomClassFeature.getRoomClass().getId());
        dto.setFeatureId(roomClassFeature.getFeature().getId());
        dto.setRoomClass(roomClassFeature.getRoomClass());
        dto.setFeature(roomClassFeature.getFeature());
        return dto;
    }

    private RoomClassFeature convertToEntity(RoomClassFeatureDTO roomClassFeatureDTO) {
        RoomClassFeature roomClassFeature = new RoomClassFeature();
        roomClassFeature.setRoomClass(roomClassFeatureDTO.getRoomClass());
        roomClassFeature.setFeature(roomClassFeatureDTO.getFeature());
        return roomClassFeature;
    }
}
