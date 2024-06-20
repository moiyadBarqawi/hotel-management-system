package com.bzu.project.repository;

import com.bzu.project.model.RoomClassBedType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomClassBedTypeRepository extends JpaRepository<RoomClassBedType, Long> {
    Optional<RoomClassBedType> findByRoomClassIdAndBedTypeId(Long roomClassId, Long bedTypeId);
    void deleteByRoomClassIdAndBedTypeId(Long roomClassId, Long bedTypeId);
}
