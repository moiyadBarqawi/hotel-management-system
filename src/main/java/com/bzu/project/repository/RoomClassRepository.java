package com.bzu.project.repository;
import com.bzu.project.model.RoomClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomClassRepository extends JpaRepository<RoomClass, Long> {
}