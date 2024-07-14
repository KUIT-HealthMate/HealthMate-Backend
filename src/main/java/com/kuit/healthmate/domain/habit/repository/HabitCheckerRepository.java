package com.kuit.healthmate.domain.habit.repository;

import com.kuit.healthmate.domain.habit.entity.HabitChecker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitCheckerRepository extends JpaRepository<HabitChecker,Long> {
}
