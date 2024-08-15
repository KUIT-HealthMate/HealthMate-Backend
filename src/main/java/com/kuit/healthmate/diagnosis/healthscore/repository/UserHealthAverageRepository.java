package com.kuit.healthmate.diagnosis.healthscore.repository;

import com.kuit.healthmate.diagnosis.healthscore.domain.UserHealthAverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface UserHealthAverageRepository extends JpaRepository<UserHealthAverage, Long> {
    @Transactional
    @Query("UPDATE UserHealthAverage u SET " +
            "u.dailyLifestyleAverage = :dailyLifestyleAverage, " +
            "u.dailySleepPatternAverage = :dailySleepPatternAverage, " +
            "u.dailyMealPatternAverage = :dailyMealPatternAverage, " +
            "u.weeklyLifestyleAverage = :weeklyLifestyleAverage, " +
            "u.weeklySleepPatternAverage = :weeklySleepPatternAverage, " +
            "u.weeklyMealPatternAverage = :weeklyMealPatternAverage, " +
            "u.monthlyLifestyleAverage = :monthlyLifestyleAverage, " +
            "u.monthlySleepPatternAverage = :monthlySleepPatternAverage, " +
            "u.monthlyMealPatternAverage = :monthlyMealPatternAverage, " +
            "u.dailyLifeStyleRegularnessAverage = :dailyLifeStyleRegularnessAverage, " +
            "u.dailyLifeStyleImmersionAverage = :dailyLifeStyleImmersionAverage, " +
            "u.dailyLifeStylePostureAverage = :dailyLifeStylePostureAverage, " +
            "u.dailyMealRegularityAverage = :dailyMealRegularityAverage, " +
            "u.dailyMealNutritionIntakeAverage = :dailyMealNutritionIntakeAverage, " +
            "u.dailyMealAlcoholFrequencyAverage = :dailyMealAlcoholFrequencyAverage, " +
            "u.dailySleepRegularityAverage = :dailySleepRegularityAverage, " +
            "u.dailySleepQualityAverage = :dailySleepQualityAverage, " +
            "u.dailySleepFocusAverage = :dailySleepFocusAverage " +
            "WHERE u.id = :id")
    void updateUserHealthAverage(Long id, int dailyLifestyleAverage, int dailyMealPatternAverage, int dailySleepPatternAverage,
                                 int weeklyLifestyleAverage, int weeklyMealPatternAverage, int weeklySleepPatternAverage,
                                 int monthlyLifestyleAverage, int monthlyMealPatternAverage, int monthlySleepPatternAverage,
                                 int dailyLifeStyleRegularnessAverage, int dailyLifeStyleImmersionAverage, int dailyLifeStylePostureAverage,
                                 int dailyMealRegularityAverage, int dailyMealNutritionIntakeAverage, int dailyMealAlcoholFrequencyAverage,
                                 int dailySleepRegularityAverage, int dailySleepQualityAverage, int dailySleepFocusAverage);
}
