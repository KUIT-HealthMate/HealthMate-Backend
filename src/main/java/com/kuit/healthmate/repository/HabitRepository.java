package com.kuit.healthmate.repository;

import com.kuit.healthmate.domain.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRepository  extends JpaRepository<Habit, Long> {
}
