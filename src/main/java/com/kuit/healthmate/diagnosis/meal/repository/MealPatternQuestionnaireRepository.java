package com.kuit.healthmate.diagnosis.meal.repository;

import com.kuit.healthmate.diagnosis.meal.domain.MealPatternQuestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealPatternQuestionnaireRepository extends JpaRepository<MealPatternQuestionnaire,Long> {
}
