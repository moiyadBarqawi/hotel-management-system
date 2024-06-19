package com.bzu.project.controllers;

import com.bzu.project.dto.AddonDTO;
import com.bzu.project.service.AddonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addons")
public class AddonController {

    @Autowired
    private AddonService addonService;

    @GetMapping
    public List<AddonDTO> getAllAddons() {
        return addonService.getAllAddons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddonDTO> getAddonById(@PathVariable Long id) {
        AddonDTO addonDTO = addonService.getAddonById(id);
        return addonDTO != null ? ResponseEntity.ok(addonDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public AddonDTO createAddon(@RequestBody AddonDTO addonDTO) {
        return addonService.createAddon(addonDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddonDTO> updateAddon(@PathVariable Long id, @RequestBody AddonDTO addonDTO) {
        AddonDTO updatedAddon = addonService.updateAddon(id, addonDTO);
        return updatedAddon != null ? ResponseEntity.ok(updatedAddon) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddon(@PathVariable Long id) {
        addonService.deleteAddon(id);
        return ResponseEntity.noContent().build();
    }
}