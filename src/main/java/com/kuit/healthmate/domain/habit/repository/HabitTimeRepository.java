package com.kuit.healthmate.domain.habit.repository;

import com.kuit.healthmate.domain.habit.entity.HabitTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitTimeRepository extends JpaRepository<HabitTime,Long> {

}

