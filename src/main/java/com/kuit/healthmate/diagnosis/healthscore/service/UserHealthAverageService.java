package com.kuit.healthmate.diagnosis.healthscore.service;

import com.kuit.healthmate.diagnosis.healthscore.domain.UserHealthAverage;

public interface UserHealthAverageService {
    void updateAverage(int dailyLifeStyleAverage, int dailyMealPatterAverage, int dailySleepPatterAverage,
                       int weekLifeStyleAverage, int weekMealPatterAverage, int weekSleepPatterAverage,
                       int monthLifeStyleAverage, int monthMealPatterAverage, int monthSleepPatterAverage);

    UserHealthAverage getAverage();
}
