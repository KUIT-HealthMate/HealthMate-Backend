package com.kuit.healthmate.diagnosis.healthscore.repository;

import com.kuit.healthmate.diagnosis.healthscore.domain.UserHealthAverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserHealthAverageRepository extends JpaRepository<UserHealthAverage, Long> {
}
