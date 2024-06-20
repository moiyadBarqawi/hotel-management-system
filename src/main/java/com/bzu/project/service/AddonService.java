package com.bzu.project.service;

import com.bzu.project.dto.AddonDTO;
import com.bzu.project.exception.ResourceNotFoundException;
import com.bzu.project.model.Addon;
import com.bzu.project.repository.AddonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddonService {

    @Autowired
    private AddonRepository addonRepository;

    public Page<AddonDTO> getAllAddons(PageRequest pageRequest) {
        Page<Addon> addons = addonRepository.findAll(pageRequest);
        return addons.map(this::convertToDTO);
    }

    public AddonDTO getAddonById(Long id) {
        Addon addon = addonRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Addon not found with id " + id));
        return convertToDTO(addon);
    }

    public AddonDTO createAddon(AddonDTO addonDTO) {
        Addon addon = convertToEntity(addonDTO);
        Addon savedAddon = addonRepository.save(addon);
        return convertToDTO(savedAddon);
    }

    public AddonDTO updateAddon(Long id, AddonDTO addonDTO) {
        Addon addon = addonRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Addon not found with id " + id));
        addon.setAddonName(addonDTO.getAddonName());
        addon.setPrice(addonDTO.getPrice());
        Addon updatedAddon = addonRepository.save(addon);
        return convertToDTO(updatedAddon);
    }

    public void deleteAddon(Long id) {
        Addon addon = addonRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Addon not found with id " + id));
        addonRepository.delete(addon);
    }

    private Addon convertToEntity(AddonDTO addonDTO) {
        Addon addon = new Addon();
        addon.setId(addonDTO.getId());
        addon.setAddonName(addonDTO.getAddonName());
        addon.setPrice(addonDTO.getPrice());
        return addon;
    }

    private AddonDTO convertToDTO(Addon addon) {
        AddonDTO addonDTO = new AddonDTO();
        addonDTO.setId(addon.getId());
        addonDTO.setAddonName(addon.getAddonName());
        addonDTO.setPrice(addon.getPrice());
        return addonDTO;
    }
}
