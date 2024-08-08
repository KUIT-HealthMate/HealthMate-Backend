package com.kuit.healthmate.diagnosis.sleep.repository;

import com.kuit.healthmate.diagnosis.meal.domain.MealPatternQuestionnaire;
import com.kuit.healthmate.diagnosis.sleep.domain.SleepPatternQuestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SleepPatternQuestionnaireRepository extends JpaRepository<SleepPatternQuestionnaire,Long> {
    @Query("SELECT s FROM SleepPatternQuestionnaire s WHERE s.timestamp >= :StartDate AND s.timestamp <= :EndDate")
    List<SleepPatternQuestionnaire> findByDate(LocalDateTime StartDate, LocalDateTime EndDate);
}
