package com.kuit.healthmate.diagnosis.meal.repository;

import com.kuit.healthmate.diagnosis.meal.domain.MealPatternQuestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MealPatternQuestionnaireRepository extends JpaRepository<MealPatternQuestionnaire,Long> {

    @Query("SELECT m FROM MealPatternQuestionnaire m WHERE m.timestamp >= :StartDate AND m.timestamp <= :EndDate")
    List<MealPatternQuestionnaire> findByDate(LocalDateTime StartDate, LocalDateTime EndDate);
}
