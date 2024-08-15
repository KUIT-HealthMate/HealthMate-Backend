package com.kuit.healthmate.diagnosis.healthscore.service;

import com.kuit.healthmate.diagnosis.healthscore.domain.UserHealthAverage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface UserHealthAverageService {
    void insertAverage(int dailyLifeStyleAverage, int dailyMealPatterAverage, int dailySleepPatterAverage,
                       int weekLifeStyleAverage, int weekMealPatterAverage, int weekSleepPatterAverage,
                       int monthLifeStyleAverage, int monthMealPatterAverage, int monthSleepPatterAverage,
                       int dailyLifeStyleRegularnessAverage, int dailyLifeStyleImmersionAverage, int dailyLifeStylePostureAverage,
                       int dailyMealRegularityAverage, int dailyMealNutritionIntakeAverage, int dailyMealAlcoholFrequencyAverage,
                       int dailySleepRegularityAverage, int dailySleepQualityAverage, int dailySleepFocusAverage
    );

    List<UserHealthAverage> getAverageByDate(LocalDate startDate, LocalDate endDate);
}
