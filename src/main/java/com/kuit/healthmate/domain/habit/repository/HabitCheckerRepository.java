package com.kuit.healthmate.domain.habit.repository;

import com.kuit.healthmate.domain.habit.entity.Habit;
import com.kuit.healthmate.domain.habit.entity.HabitChecker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface HabitCheckerRepository extends JpaRepository<HabitChecker,Long> {
    Optional<HabitChecker> findByHabitAndCreatedAtBetween(Habit habit, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
