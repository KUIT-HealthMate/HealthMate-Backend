package com.kuit.healthmate.challenge.habit.repository;

import com.kuit.healthmate.challenge.habit.domain.HabitTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitTimeRepository extends JpaRepository<HabitTime,Long> {

}

