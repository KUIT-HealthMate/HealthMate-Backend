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
                              int monthLifeStyleAverage, int monthMealPatterAverage, int monthSleepPatterAverage) {
        userHealthAverageRepository.updateUserHealthAverage(1,dailyLifeStyleAverage,dailyMealPatterAverage,dailySleepPatterAverage
                weekLifeStyleAverage,weekMealPatterAverage,weekSleepPatterAverage,monthLifeStyleAverage,monthMealPatterAverage,monthSleepPatterAverage);
    }

    @Override
    public UserHealthAverage getAverage() {
        return userHealthAverageRepository.findById(Long.valueOf(1)).get();
    }
}
