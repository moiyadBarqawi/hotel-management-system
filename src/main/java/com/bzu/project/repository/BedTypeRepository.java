package com.bzu.project.repository;

import com.bzu.project.model.BedType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BedTypeRepository extends JpaRepository<BedType, Long> {
}