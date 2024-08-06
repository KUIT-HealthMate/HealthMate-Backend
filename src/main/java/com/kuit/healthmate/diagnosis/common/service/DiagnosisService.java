package com.kuit.healthmate.diagnosis.common.service;

import com.kuit.healthmate.chatgpt.dto.response.LifeStyleResponse;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternResponse;
import com.kuit.healthmate.chatgpt.dto.response.SleepPatternResponse;
import com.kuit.healthmate.chatgpt.service.GptService;
import com.kuit.healthmate.chatgpt.util.formatter.LifeStyleTodayFormatter;
import com.kuit.healthmate.chatgpt.util.formatter.month.LifeStyleMonthFormatter;
import com.kuit.healthmate.diagnosis.dto.LifeStyleDto;
import com.kuit.healthmate.diagnosis.dto.MealPatternDto;
import com.kuit.healthmate.diagnosis.dto.PostDiagnosisRequest;
import com.kuit.healthmate.diagnosis.dto.SleepPatternDto;
import com.kuit.healthmate.diagnosis.gpt.domain.GptResultToday;
import com.kuit.healthmate.diagnosis.gpt.repository.GptResultRepository;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiagnosisService {
    private final LifeStyleQuestionnaireRepository lifeStyleQuestionnaireRepository;
    private final MealPatternQuestionnaireRepository mealPatternQuestionnaireRepository;
    private final SleepPatternQuestionnaireRepository sleepPatternQuestionnaireRepository;
    private final SymptomQuestionnaireRepository symptomQuestionnaireRepository;
    private final GptResultRepository gptResultRepository;
    private final GptService gptService;

    @Transactional
    public void saveDiagnosisResult(PostDiagnosisRequest postDiagnosisRequest) {
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
    }

    public void saveGptResult(LifeStyleResponse lifeStyleToday, MealPatternResponse mealPatternToday, SleepPatternResponse sleepPatternToday) {
        GptResultToday gptResult = GptResultToday.builder()
                .date(LocalDate.now())
                .lifeStyleToday(lifeStyleToday)
                .mealPatternToday(mealPatternToday)
                .sleepPatternToday(sleepPatternToday).build();
        gptResultRepository.save(gptResult);
    }

    public LifeStyleResponse createMonthlyDiagnosis(Long userId, List<LifeStyleQuestionnaire> dailyDiagnoses) {
        //gptRequest 만들고
        LifeStyleMonthFormatter lifeStyleMonthFormatter = new LifeStyleMonthFormatter();
        String message = lifeStyleMonthFormatter.formatResponse(dailyDiagnoses);
        //gptservice 호출
        log.info(gptService.getPrompt(message));
        // 결과를 parser로 엔티티로 변환
        // 월간 엔티티에 저장하고 결과 반환
        return null;
    }
}
