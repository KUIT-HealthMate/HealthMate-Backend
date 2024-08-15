package com.kuit.healthmate.diagnosis.healthscore.repository;

import com.kuit.healthmate.diagnosis.healthscore.domain.UserHealthAverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface UserHealthAverageRepository extends JpaRepository<UserHealthAverage, Long> {
    @Query("SELECT u FROM UserHealthAverage u WHERE u.createdAt BETWEEN :startDate AND :endDate")
    List<UserHealthAverage> getAverageByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
