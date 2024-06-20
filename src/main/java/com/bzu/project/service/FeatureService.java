package com.bzu.project.service;

import com.bzu.project.dto.FeatureDTO;
import com.bzu.project.model.Feature;
import com.bzu.project.repository.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeatureService {

    @Autowired
    private FeatureRepository featureRepository;

    public List<FeatureDTO> getAllFeatures() {
        return featureRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public FeatureDTO getFeatureById(Long id) {
        Optional<Feature> feature = featureRepository.findById(id);
        return feature.map(this::convertToDTO).orElse(null);
    }

    public FeatureDTO createFeature(FeatureDTO featureDTO) {
        Feature feature = convertToEntity(featureDTO);
        return convertToDTO(featureRepository.save(feature));
    }

    public FeatureDTO updateFeature(Long id, FeatureDTO featureDTO) {
        Optional<Feature> existingFeature = featureRepository.findById(id);
        if (existingFeature.isPresent()) {
            Feature feature = existingFeature.get();
            feature.setFeatureName(featureDTO.getFeatureName());
            return convertToDTO(featureRepository.save(feature));
        } else {
            return null;
        }
    }

    public void deleteFeature(Long id) {
        featureRepository.deleteById(id);
    }

    private FeatureDTO convertToDTO(Feature feature) {
        FeatureDTO featureDTO = new FeatureDTO();
        featureDTO.setId(feature.getId());
        featureDTO.setFeatureName(feature.getFeatureName());
        return featureDTO;
    }

    private Feature convertToEntity(FeatureDTO featureDTO) {
        Feature feature = new Feature();
        feature.setId(featureDTO.getId());
        feature.setFeatureName(featureDTO.getFeatureName());
        return feature;
    }
}
