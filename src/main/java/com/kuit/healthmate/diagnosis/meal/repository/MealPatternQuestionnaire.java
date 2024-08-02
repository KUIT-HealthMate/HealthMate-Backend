package com.kuit.healthmate.diagnosis.meal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealPatternQuestionnaire extends JpaRepository<MealPatternQuestionnaire,Long> {
}
