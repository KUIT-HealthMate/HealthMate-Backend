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
            "u.monthlyMealPatternAverage = :monthlyMealPatternAverage " +
            "WHERE u.id = :id")
    void updateUserHealthAverage(Long id, int dailyLifestyleAverage, int dailySleepPatternAverage,
                                 int dailyMealPatternAverage, int weeklyLifestyleAverage,
                                 int weeklySleepPatternAverage, int weeklyMealPatternAverage,
                                 int monthlyLifestyleAverage, int monthlySleepPatternAverage,
                                 int monthlyMealPatternAverage);
}
