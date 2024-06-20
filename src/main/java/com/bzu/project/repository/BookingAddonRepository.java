package com.bzu.project.repository;

import com.bzu.project.model.BookingAddon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingAddonRepository extends JpaRepository<BookingAddon, Long> {
    Optional<BookingAddon> findByBookingIdAndAddonId(Long bookingId, Long addonId);
    void deleteByBookingIdAndAddonId(Long bookingId, Long addonId);
}
