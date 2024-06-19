package com.bzu.project.dto;

import org.springframework.hateoas.RepresentationModel;

public class FeatureDTO  extends RepresentationModel<FeatureDTO> {
    private Long id;
    private String featureName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }
}