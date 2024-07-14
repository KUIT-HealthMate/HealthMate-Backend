package com.kuit.healthmate.domain.habit.repository;

import com.kuit.healthmate.domain.habit.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitRepository  extends JpaRepository<Habit, Long> {
}
