package com.kuit.healthmate.diagnosis.healthscore.service;

import com.kuit.healthmate.diagnosis.healthscore.domain.UserHealthAverage;
import com.kuit.healthmate.diagnosis.healthscore.repository.UserHealthAverageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserHealthAverageServiceImpl implements UserHealthAverageService{
    private final UserHealthAverageRepository userHealthAverageRepository;
    @Transactional
    public void updateAverage(int dailyLifeStyleAverage, int dailyMealPatterAverage, int dailySleepPatterAverage,
                              int weekLifeStyleAverage, int weekMealPatterAverage, int weekSleepPatterAverage,
                              int monthLifeStyleAverage, int monthMealPatterAverage, int monthSleepPatterAverage,
                              int dailyLifeStyleRegularnessAverage, int dailyLifeStyleImmersionAverage, int dailyLifeStylePostureAverage,
                              int dailyMealRegularityAverage, int dailyMealNutritionIntakeAverage, int dailyMealAlcoholFrequencyAverage,
                              int dailySleepRegularityAverage, int dailySleepQualityAverage, int dailySleepFocusAverage) {
        userHealthAverageRepository.updateUserHealthAverage(1L,dailyLifeStyleAverage,dailyMealPatterAverage,dailySleepPatterAverage,
                weekLifeStyleAverage,weekMealPatterAverage,weekSleepPatterAverage,monthLifeStyleAverage,monthMealPatterAverage,monthSleepPatterAverage,
                dailyLifeStyleRegularnessAverage,dailyLifeStyleImmersionAverage,dailyLifeStylePostureAverage,
                dailyMealRegularityAverage,dailyMealNutritionIntakeAverage,dailyMealAlcoholFrequencyAverage,
                dailySleepRegularityAverage,dailySleepQualityAverage,dailySleepFocusAverage
                );
    }

    @Override
    public UserHealthAverage getAverage() {
        return userHealthAverageRepository.findById(Long.valueOf(1)).get();
    }
}
