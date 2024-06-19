package com.bzu.project.service;

import com.bzu.project.dto.AddonDTO;
import com.bzu.project.model.Addon;
import com.bzu.project.repository.AddonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddonService {

    @Autowired
    private AddonRepository addonRepository;

    public List<AddonDTO> getAllAddons() {
        List<Addon> addons = addonRepository.findAll();
        return addons.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AddonDTO getAddonById(Long id) {
        Optional<Addon> addonOptional = addonRepository.findById(id);
        return addonOptional.map(this::convertToDTO).orElse(null);
    }

    public AddonDTO createAddon(AddonDTO addonDTO) {
        Addon addon = convertToEntity(addonDTO);
        Addon savedAddon = addonRepository.save(addon);
        return convertToDTO(savedAddon);
    }

    public AddonDTO updateAddon(Long id, AddonDTO addonDTO) {
        Optional<Addon> optionalAddon = addonRepository.findById(id);
        if (optionalAddon.isPresent()) {
            Addon addon = optionalAddon.get();
            addon.setAddonName(addonDTO.getAddonName());
            addon.setPrice(addonDTO.getPrice());
            Addon updatedAddon = addonRepository.save(addon);
            return convertToDTO(updatedAddon);
        } else {
            return null;
        }
    }

    public void deleteAddon(Long id) {
        addonRepository.deleteById(id);
    }

    private Addon convertToEntity(AddonDTO addonDTO) {
        Addon addon = new Addon();
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
