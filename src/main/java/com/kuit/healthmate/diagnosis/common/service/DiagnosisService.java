package com.kuit.healthmate.diagnosis.common.service;

import com.kuit.healthmate.chatgpt.dto.response.LifeStyleToday;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternToday;
import com.kuit.healthmate.chatgpt.dto.response.SleepPatternToday;
import com.kuit.healthmate.chatgpt.service.GptService;
import com.kuit.healthmate.diagnosis.dto.LifeStyleDto;
import com.kuit.healthmate.diagnosis.dto.MealPatternDto;
import com.kuit.healthmate.diagnosis.dto.PostDiagnosisRequest;
import com.kuit.healthmate.diagnosis.dto.SleepPatternDto;
import com.kuit.healthmate.diagnosis.life.domain.LifeStyleQuestionnaire;
import com.kuit.healthmate.diagnosis.life.repository.LifeStyleQuestionnaireRepository;
import com.kuit.healthmate.diagnosis.meal.domain.MealPatternQuestionnaire;
import com.kuit.healthmate.diagnosis.meal.repository.MealPatternQuestionnaireRepository;
import com.kuit.healthmate.diagnosis.sleep.domain.SleepPatternQuestionnaire;
import com.kuit.healthmate.diagnosis.sleep.repository.SleepPatternQuestionnaireRepository;
import com.kuit.healthmate.diagnosis.symtom.domain.SymptomInfo;
import com.kuit.healthmate.diagnosis.symtom.domain.SymptomQuestionnaire;
import com.kuit.healthmate.diagnosis.symtom.repository.SymptomQuestionnaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiagnosisService {
    private final LifeStyleQuestionnaireRepository lifeStyleQuestionnaireRepository;
    private final MealPatternQuestionnaireRepository mealPatternQuestionnaireRepository;
    private final SleepPatternQuestionnaireRepository sleepPatternQuestionnaireRepository;
    private final SymptomQuestionnaireRepository symptomQuestionnaireRepository;
    private final GptService gptService;

    @Transactional
    public Boolean saveDiagnosisResult(PostDiagnosisRequest postDiagnosisRequest) {
        //추후에 userId 로 매핑시켜주기
        LifeStyleDto lifeStyleDto =  postDiagnosisRequest.getLifeStyleDto();
        SleepPatternDto sleepPatternDto = postDiagnosisRequest.getSleepPatternDto();
        MealPatternDto  mealPatternDto = postDiagnosisRequest.getMealPatternDto();
        List<SymptomInfo> symptomInfoList = postDiagnosisRequest.getSymptomInfos();
        int listSize  = symptomInfoList.size();
        SymptomQuestionnaire symptomQuestionnaire = null;

        SymptomQuestionnaire.SymptomQuestionnaireBuilder builder = SymptomQuestionnaire.builder();
        if (listSize > 0) {
            builder.first(symptomInfoList.get(0));
        }
        if (listSize > 1) {
            builder.second(symptomInfoList.get(1));
        }
        if (listSize > 2){
            builder.third(symptomInfoList.get(2));
        }
        builder.timestamp(LocalDateTime.now());
        symptomQuestionnaire = builder.build();

        //객체 생성
        LifeStyleQuestionnaire lifeStyleQuestionnaire = LifeStyleQuestionnaire.builder()
                .environmentScore(lifeStyleDto.getEnvironmentScore())
                .focusTimeScore(lifeStyleDto.getFocusTimeScore())
                .exerciseTimeScore(lifeStyleDto.getExerciseTimeScore())
                .coffeeConsumptionScore(lifeStyleDto.getCoffeeConsumptionScore())
                .postureDiscomfortScore(lifeStyleDto.getPostureDiscomfortScore())
                .timestamp(LocalDateTime.now()).build();
        MealPatternQuestionnaire mealPatternQuestionnaire = MealPatternQuestionnaire.builder()
                .mealDurationScore(mealPatternDto.getMealDurationScore())
                .mealRemark(mealPatternDto.getMealRemark())
                .foodType(mealPatternDto.getFoodType())
                .seasoningConsumptionScore(mealPatternDto.getSeasoningConsumptionScore())
                .screenUsage(mealPatternDto.getScreenUsage())
                .regularMealTimeScore(mealPatternDto.getRegularMealTimeScore())
                .mealTimeScore(mealPatternDto.getMealTimeScore())
                .timestamp(LocalDateTime.now()).build();
        SleepPatternQuestionnaire sleepPatternQuestionnaire = SleepPatternQuestionnaire.builder()
                .sleepDurationScore(sleepPatternDto.getSleepDurationScore())
                .morningFatigueScore(sleepPatternDto.getMorningFatigueScore())
                .sleepRemarkScore(sleepPatternDto.getSleepRemarkScore())
                .peakConditionTimeScore(sleepPatternDto.getPeakConditionTimeScore())
                .timestamp(LocalDateTime.now()).build();


        lifeStyleQuestionnaireRepository.save(lifeStyleQuestionnaire);
        mealPatternQuestionnaireRepository.save(mealPatternQuestionnaire);
        sleepPatternQuestionnaireRepository.save(sleepPatternQuestionnaire);
        symptomQuestionnaireRepository.save(symptomQuestionnaire);
        return true;
    }

    public void saveGptResult(LifeStyleToday lifeStyleToday, MealPatternToday mealPatternToday, SleepPatternToday sleepPatternToday) {

    }
}
