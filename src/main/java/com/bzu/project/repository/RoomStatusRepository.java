package com.bzu.project.repository;

import com.bzu.project.model.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomStatusRepository extends JpaRepository<RoomStatus, Long> {
}