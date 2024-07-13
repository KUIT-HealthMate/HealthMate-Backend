package com.kuit.healthmate.repository;

import com.kuit.healthmate.domain.HabitChecker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitCheckerRepository extends JpaRepository<HabitChecker,Long> {
}
