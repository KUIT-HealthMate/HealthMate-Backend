package com.kuit.healthmate.diagnosis.healthscore.service;

import com.kuit.healthmate.diagnosis.healthscore.domain.UserHealthAverage;
import com.kuit.healthmate.diagnosis.healthscore.repository.UserHealthAverageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserHealthAverageServiceImpl implements UserHealthAverageService{
    private final UserHealthAverageRepository userHealthAverageRepository;
    @Transactional
    public void insertAverage(int dailyLifeStyleAverage, int dailyMealPatterAverage, int dailySleepPatterAverage,
                              int weekLifeStyleAverage, int weekMealPatterAverage, int weekSleepPatterAverage,
                              int monthLifeStyleAverage, int monthMealPatterAverage, int monthSleepPatterAverage,
                              int dailyLifeStyleRegularnessAverage, int dailyLifeStyleImmersionAverage, int dailyLifeStylePostureAverage,
                              int dailyMealRegularityAverage, int dailyMealNutritionIntakeAverage, int dailyMealAlcoholFrequencyAverage,
                              int dailySleepRegularityAverage, int dailySleepQualityAverage, int dailySleepFocusAverage) {

        // 엔티티를 생성자를 통해 초기화
        UserHealthAverage userHealthAverage = new UserHealthAverage(
                (double) dailyLifeStyleAverage,
                (double) dailyMealPatterAverage,
                (double) dailySleepPatterAverage,
                (double) weekLifeStyleAverage,
                (double) weekMealPatterAverage,
                (double) weekSleepPatterAverage,
                (double) monthLifeStyleAverage,
                (double) monthMealPatterAverage,
                (double) monthSleepPatterAverage,
                (double) dailyLifeStyleRegularnessAverage,
                (double) dailyLifeStyleImmersionAverage,
                (double) dailyLifeStylePostureAverage,
                (double) dailyMealRegularityAverage,
                (double) dailyMealNutritionIntakeAverage,
                (double) dailyMealAlcoholFrequencyAverage,
                (double) dailySleepRegularityAverage,
                (double) dailySleepQualityAverage,
                (double) dailySleepFocusAverage
        );

        // 엔티티를 데이터베이스에 저장
        userHealthAverageRepository.save(userHealthAverage);
    }

    @Override
    public List<UserHealthAverage> getAverageByDate(LocalDate startDate, LocalDate endDate) {
        return userHealthAverageRepository.getAverageByDate(startDate, endDate);
    }
}
