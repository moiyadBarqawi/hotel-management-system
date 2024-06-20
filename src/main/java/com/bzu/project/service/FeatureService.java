package com.bzu.project.service;

import com.bzu.project.dto.FeatureDTO;
import com.bzu.project.exception.ResourceNotFoundException;
import com.bzu.project.model.Feature;
import com.bzu.project.repository.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeatureService {

    @Autowired
    private FeatureRepository featureRepository;

    public Page<FeatureDTO> getAllFeatures(PageRequest pageRequest) {
        Page<Feature> features = featureRepository.findAll(pageRequest);
        return features.map(this::convertToDTO);
    }

    public FeatureDTO getFeatureById(Long id) {
        Feature feature = featureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feature not found with id " + id));
        return convertToDTO(feature);
    }

    public FeatureDTO createFeature(FeatureDTO featureDTO) {
        Feature feature = convertToEntity(featureDTO);
        return convertToDTO(featureRepository.save(feature));
    }

    public FeatureDTO updateFeature(Long id, FeatureDTO featureDTO) {
        Feature feature = featureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Feature not found with id " + id));
        feature.setFeatureName(featureDTO.getFeatureName());
        return convertToDTO(featureRepository.save(feature));
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
