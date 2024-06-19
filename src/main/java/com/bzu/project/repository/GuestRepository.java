package com.bzu.project.repository;

import com.bzu.project.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    Optional<Object> findByEmail(String username);
}