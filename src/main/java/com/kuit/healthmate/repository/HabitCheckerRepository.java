package com.kuit.healthmate.repository;

import com.kuit.healthmate.domain.HabitChecker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitCheckerRepository extends JpaRepository<HabitChecker,Long> {
}
