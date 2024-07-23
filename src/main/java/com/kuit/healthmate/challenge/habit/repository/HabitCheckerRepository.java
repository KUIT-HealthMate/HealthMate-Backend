package com.kuit.healthmate.challenge.habit.repository;

import com.kuit.healthmate.challenge.habit.domain.HabitChecker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitCheckerRepository extends JpaRepository<HabitChecker,Long> {
}
