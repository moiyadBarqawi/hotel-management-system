package com.bzu.project.service;

import com.bzu.project.dto.BedTypeDTO;
import com.bzu.project.exception.ResourceNotFoundException;
import com.bzu.project.model.BedType;
import com.bzu.project.repository.BedTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BedTypeService {

    @Autowired
    private BedTypeRepository bedTypeRepository;

    public Page<BedTypeDTO> getAllBedTypes(PageRequest pageRequest) {
        Page<BedType> bedTypes = bedTypeRepository.findAll(pageRequest);
        return bedTypes.map(this::convertToDTO);
    }

    public BedTypeDTO getBedTypeById(Long id) {
        BedType bedType = bedTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BedType not found with id " + id));
        return convertToDTO(bedType);
    }

    public BedTypeDTO createBedType(BedTypeDTO bedTypeDTO) {
        BedType bedType = convertToEntity(bedTypeDTO);
        BedType savedBedType = bedTypeRepository.save(bedType);
        return convertToDTO(savedBedType);
    }

    public BedTypeDTO updateBedType(Long id, BedTypeDTO bedTypeDTO) {
        BedType bedType = bedTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BedType not found with id " + id));
        bedType.setBedTypeName(bedTypeDTO.getBedTypeName());
        BedType updatedBedType = bedTypeRepository.save(bedType);
        return convertToDTO(updatedBedType);
    }

    public void deleteBedType(Long id) {
        BedType bedType = bedTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BedType not found with id " + id));
        bedTypeRepository.delete(bedType);
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
