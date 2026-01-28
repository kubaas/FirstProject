package com.example.imo.repo;

import com.example.imo.domain.WeeklyCheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WeeklyCheckInRepository extends JpaRepository<WeeklyCheckIn, UUID> {
  Optional<WeeklyCheckIn> findTopByUserIdOrderByCreatedAtDesc(UUID userId);
}

