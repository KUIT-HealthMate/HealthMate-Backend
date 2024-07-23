package com.kuit.healthmate.challenge.habit.repository;

import com.kuit.healthmate.challenge.habit.domain.Habit;
import com.kuit.healthmate.challenge.habit.domain.HabitChecker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface HabitCheckerRepository extends JpaRepository<HabitChecker,Long> {
    Optional<HabitChecker> findByHabitAndCreatedAtBetween(Habit habit, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
