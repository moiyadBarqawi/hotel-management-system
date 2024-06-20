package com.bzu.project.service;

import com.bzu.project.dto.BedTypeDTO;
import com.bzu.project.model.BedType;
import com.bzu.project.repository.BedTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BedTypeService {

    @Autowired
    private BedTypeRepository bedTypeRepository;

    public List<BedTypeDTO> getAllBedTypes() {
        return bedTypeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BedTypeDTO getBedTypeById(Long id) {
        Optional<BedType> bedType = bedTypeRepository.findById(id);
        return bedType.map(this::convertToDTO).orElse(null);
    }

    public BedTypeDTO createBedType(BedTypeDTO bedTypeDTO) {
        BedType bedType = convertToEntity(bedTypeDTO);
        return convertToDTO(bedTypeRepository.save(bedType));
    }

    public BedTypeDTO updateBedType(Long id, BedTypeDTO bedTypeDTO) {
        Optional<BedType> existingBedType = bedTypeRepository.findById(id);
        if (existingBedType.isPresent()) {
            BedType bedType = existingBedType.get();
            bedType.setBedTypeName(bedTypeDTO.getBedTypeName());
            return convertToDTO(bedTypeRepository.save(bedType));
        } else {
            return null;
        }
    }

    public void deleteBedType(Long id) {
        bedTypeRepository.deleteById(id);
    }

    private BedTypeDTO convertToDTO(BedType bedType) {
        BedTypeDTO bedTypeDTO = new BedTypeDTO();
        bedTypeDTO.setId(bedType.getId());
        bedTypeDTO.setBedTypeName(bedType.getBedTypeName());
        return bedTypeDTO;
    }

    private BedType convertToEntity(BedTypeDTO bedTypeDTO) {
        BedType bedType = new BedType();
        bedType.setId(bedTypeDTO.getId());
        bedType.setBedTypeName(bedTypeDTO.getBedTypeName());
        return bedType;
    }
}
