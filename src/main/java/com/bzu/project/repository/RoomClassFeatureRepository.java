package com.bzu.project.repository;

import com.bzu.project.model.RoomClassFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoomClassFeatureRepository extends JpaRepository<RoomClassFeature, Long> {
    Optional<RoomClassFeature> findByRoomClassIdAndFeatureId(Long roomClassId, Long featureId);
    void deleteByRoomClassIdAndFeatureId(Long roomClassId, Long featureId);
}
