package com.kuit.healthmate.diagnosis.common.service;

import com.kuit.healthmate.chatgpt.dto.response.LifeStyleResponse;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternResponse;
import com.kuit.healthmate.chatgpt.dto.response.SleepPatternResponse;
import com.kuit.healthmate.chatgpt.service.GptService;
import com.kuit.healthmate.chatgpt.util.formatter.month.LifeStyleMonthFormatter;
import com.kuit.healthmate.diagnosis.dto.*;
import com.kuit.healthmate.diagnosis.gpt.domain.GptMonthResult;
import com.kuit.healthmate.diagnosis.gpt.domain.GptResult;
import com.kuit.healthmate.diagnosis.gpt.domain.GptWeekResult;
import com.kuit.healthmate.diagnosis.gpt.repository.GptMonthResultRepository;
import com.kuit.healthmate.diagnosis.gpt.repository.GptResultRepository;
import com.kuit.healthmate.diagnosis.gpt.repository.GptWeekResultRepository;
import com.kuit.healthmate.diagnosis.life.domain.LifeStyleQuestionnaire;
import com.kuit.healthmate.diagnosis.life.repository.LifeStyleQuestionnaireRepository;
import com.kuit.healthmate.diagnosis.meal.domain.MealPatternQuestionnaire;
import com.kuit.healthmate.diagnosis.meal.repository.MealPatternQuestionnaireRepository;
import com.kuit.healthmate.diagnosis.sleep.domain.SleepPatternQuestionnaire;
import com.kuit.healthmate.diagnosis.sleep.repository.SleepPatternQuestionnaireRepository;
import com.kuit.healthmate.diagnosis.symtom.domain.SymptomInfo;
import com.kuit.healthmate.diagnosis.symtom.domain.SymptomQuestionnaire;
import com.kuit.healthmate.diagnosis.symtom.repository.SymptomQuestionnaireRepository;
import com.kuit.healthmate.global.exception.DiagnosisException;
import com.kuit.healthmate.global.response.ExceptionResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Map;

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
    private final GptMonthResultRepository gptMonthResultRepository;
    private final GptWeekResultRepository gptWeekResultRepository;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Transactional
    public Boolean saveDiagnosisResult(Long userId,PostDiagnosisRequest postDiagnosisRequest) {
        LifeStyleDto lifeStyleDto =  postDiagnosisRequest.getLifeStyleDto();
        SleepPatternDto sleepPatternDto = postDiagnosisRequest.getSleepPatternDto();
        MealPatternDto  mealPatternDto = postDiagnosisRequest.getMealPatternDto();
        List<SymptomInfo> symptomInfoList = postDiagnosisRequest.getSymptomInfos();
        int listSize  = symptomInfoList.size();
        SymptomQuestionnaire symptomQuestionnaire = null;

        SymptomQuestionnaire.SymptomQuestionnaireBuilder builder = SymptomQuestionnaire.builder()
                .userId(userId).user_name(postDiagnosisRequest.getUserName());
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
                .userId(userId)
                .user_name(postDiagnosisRequest.getUserName())
                .environmentScore(lifeStyleDto.getEnvironmentScore())
                .focusTimeScore(lifeStyleDto.getFocusTimeScore())
                .exerciseTimeScore(lifeStyleDto.getExerciseTimeScore())
                .coffeeConsumptionScore(lifeStyleDto.getCoffeeConsumptionScore())
                .postureDiscomfortScore(lifeStyleDto.getPostureDiscomfortScore())
                .timestamp(LocalDateTime.now()).build();
        MealPatternQuestionnaire mealPatternQuestionnaire = MealPatternQuestionnaire.builder()
                .userId(userId)
                .user_name(postDiagnosisRequest.getUserName())
                .mealDurationScore(mealPatternDto.getMealDurationScore())
                .mealRemark(mealPatternDto.getMealRemark())
                .foodType(mealPatternDto.getFoodType())
                .seasoningConsumptionScore(mealPatternDto.getSeasoningConsumptionScore())
                .screenUsage(mealPatternDto.getScreenUsage())
                .regularMealTimeScore(mealPatternDto.getRegularMealTimeScore())
                .mealTimeScore(mealPatternDto.getMealTimeScore())
                .timestamp(LocalDateTime.now()).build();
        SleepPatternQuestionnaire sleepPatternQuestionnaire = SleepPatternQuestionnaire.builder()
                .userId(userId)
                .user_name(postDiagnosisRequest.getUserName())
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

    public void saveGptResult(Long userId,LifeStyleResponse lifeStyleToday, MealPatternResponse mealPatternToday, SleepPatternResponse sleepPatternToday) {
        GptResult gptResult = GptResult.builder()
                .userId(userId)
                .date(LocalDate.now())
                .lifeStyleToday(lifeStyleToday)
                .mealPatternToday(mealPatternToday)
                .sleepPatternToday(sleepPatternToday).build();
        gptResultRepository.save(gptResult);
    }
    public void saveMonthGptResult(Long userId,LifeStyleResponse lifeStyleToday, MealPatternResponse mealPatternToday, SleepPatternResponse sleepPatternToday) {
        GptMonthResult gptMonthResult = GptMonthResult.builder()
                .userId(userId)
                .month((long) LocalDate.now().getMonthValue())
                .year((long) LocalDate.now().getYear())
                .lifeStyleToday(lifeStyleToday)
                .mealPatternToday(mealPatternToday)
                .sleepPatternToday(sleepPatternToday).build();
        gptMonthResultRepository.save(gptMonthResult);
    }
    public void saveWeekGptResult(Long userId,LifeStyleResponse lifeStyleToday, MealPatternResponse mealPatternToday, SleepPatternResponse sleepPatternToday) {
        GptWeekResult gptWeekResult = GptWeekResult.builder()
                .userId(userId)
                .year((long) LocalDate.now().getYear())
                .week((long) LocalDate.now().get(WeekFields.ISO.weekOfYear()))
                .lifeStyleToday(lifeStyleToday)
                .mealPatternToday(mealPatternToday)
                .sleepPatternToday(sleepPatternToday).build();
        gptWeekResultRepository.save(gptWeekResult);
    }
    public DiagnosisResponseDTO findDayDiagnosisResult(Long userId, String date) {
        LocalDate formatDate = LocalDate.parse(date, FORMATTER);
        GptResult gptResult = gptResultRepository.findDiagnosisResultByUserIdAndDate(userId, formatDate)
                .orElseThrow(() -> new DiagnosisException(ExceptionResponseStatus.INVALID_DIAGNOSIS_VALUE, "진단 결과가 존재하지 않습니다"));

        return new DiagnosisResponseDTO(formatDate,gptResult.getLifeStyleToday(),gptResult.getMealPatternToday(),gptResult.getSleepPatternToday());
    }
    public DiagnosisResponseDTO findWeekDiagnosisResult(Long userId, String date) {
        LocalDate formatDate = LocalDate.parse(date, FORMATTER);
        GptWeekResult gptWeekResult = gptWeekResultRepository.findDiagnosisResultByUserIdAndDate(userId, (long)formatDate.get(WeekFields.ISO.weekOfYear()),(long)formatDate.getYear())
                .orElseThrow(() -> new DiagnosisException(ExceptionResponseStatus.INVALID_DIAGNOSIS_VALUE, "진단 결과가 존재하지 않습니다"));

        return new DiagnosisResponseDTO(formatDate,gptWeekResult.getLifeStyleToday(),gptWeekResult.getMealPatternToday(),gptWeekResult.getSleepPatternToday());
    }
    public DiagnosisResponseDTO findMonthDiagnosisResult(Long userId, String date) {
        LocalDate formatDate = LocalDate.parse(date, FORMATTER);
        GptMonthResult gptMonthResult = gptMonthResultRepository.findDiagnosisResultByUserIdAndDate(userId, (long)formatDate.getMonthValue(),(long)formatDate.getYear())
                .orElseThrow(() -> new DiagnosisException(ExceptionResponseStatus.INVALID_DIAGNOSIS_VALUE, "진단 결과가 존재하지 않습니다"));

        return new DiagnosisResponseDTO(formatDate,gptMonthResult.getLifeStyleToday(),gptMonthResult.getMealPatternToday(),gptMonthResult.getSleepPatternToday());
    }
}
