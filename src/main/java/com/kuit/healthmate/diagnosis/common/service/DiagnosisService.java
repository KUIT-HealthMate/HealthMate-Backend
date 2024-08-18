package com.kuit.healthmate.diagnosis.common.service;

import com.kuit.healthmate.chatgpt.dto.response.LifeStyleResponse;
import com.kuit.healthmate.chatgpt.dto.response.MealPatternResponse;
import com.kuit.healthmate.chatgpt.dto.response.SleepPatternResponse;
import com.kuit.healthmate.chatgpt.service.GptService;
import com.kuit.healthmate.chatgpt.util.formatter.month.LifeStyleMonthFormatter;
import com.kuit.healthmate.diagnosis.dto.*;
import com.kuit.healthmate.diagnosis.dto.response.day.DiagnosisDayResponseDTO;
import com.kuit.healthmate.diagnosis.dto.response.day.LifeStyleResponseWithAverage;
import com.kuit.healthmate.diagnosis.dto.response.day.MealPatternResponseWithAverage;
import com.kuit.healthmate.diagnosis.dto.response.day.SleepPatternResponseWithAverage;
import com.kuit.healthmate.diagnosis.dto.response.month.DiagnosisMonthResponseDTO;
import com.kuit.healthmate.diagnosis.dto.response.week.DiagnosisWeekResponseDTO;
import com.kuit.healthmate.diagnosis.dto.response.week.LifeStyleWeekResponse;
import com.kuit.healthmate.diagnosis.dto.response.week.MealPatternWeekResponse;
import com.kuit.healthmate.diagnosis.dto.response.week.SleepPatternWeekResponse;
import com.kuit.healthmate.diagnosis.gpt.domain.GptMonthResult;
import com.kuit.healthmate.diagnosis.gpt.domain.GptResult;
import com.kuit.healthmate.diagnosis.gpt.domain.GptWeekResult;
import com.kuit.healthmate.diagnosis.gpt.repository.GptMonthResultRepository;
import com.kuit.healthmate.diagnosis.gpt.repository.GptResultRepository;
import com.kuit.healthmate.diagnosis.gpt.repository.GptWeekResultRepository;
import com.kuit.healthmate.diagnosis.healthscore.domain.UserHealthAverage;
import com.kuit.healthmate.diagnosis.healthscore.repository.UserHealthAverageRepository;
import com.kuit.healthmate.diagnosis.healthscore.service.UserHealthAverageService;
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

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
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
    private final UserHealthAverageService userHealthAverageService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final UserHealthAverageRepository userHealthAverageRepository;

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
                .month((long) LocalDate.now().getMonthValue())
                .lifeStyleToday(lifeStyleToday)
                .mealPatternToday(mealPatternToday)
                .sleepPatternToday(sleepPatternToday).build();
        gptWeekResultRepository.save(gptWeekResult);
    }
    public DiagnosisDayResponseDTO findDayDiagnosisResult(Long userId, String date) {
        LocalDate formatDate = LocalDate.parse(date, FORMATTER);
        GptResult gptResult = gptResultRepository.findDiagnosisResultByUserIdAndDate(userId, formatDate)
                .orElseThrow(() -> new DiagnosisException(ExceptionResponseStatus.INVALID_DIAGNOSIS_VALUE, "진단 결과가 존재하지 않습니다"));
        List<UserHealthAverage> userHealthAverages = userHealthAverageService.getAverageByDate(formatDate.minusDays(2),formatDate.minusDays(1));
        UserHealthAverage userHealthAverage = userHealthAverages.get(0);
        if(userHealthAverage == null){
             userHealthAverage = new UserHealthAverage(
                     50.0,50.0,50.0,50.0,50.0,50.0,50.0,50.0,50.0,50.0,50.0,50.0,50.0,50.0,50.0,50.0,50.0,50.0
            );
        }
        LifeStyleResponseWithAverage lifeStyleResponseWithAverage = new LifeStyleResponseWithAverage(
                userHealthAverage.getDailyLifestyleAverage(),
                userHealthAverage.getDailyLifeStyleRegularnessAverage(),
                userHealthAverage.getDailyLifeStylePostureAverage(),
                userHealthAverage.getDailyLifeStyleImmersionAverage(),
                gptResult.getLifeStyleToday()
        );
        MealPatternResponseWithAverage mealPatternResponseWithAverage = new MealPatternResponseWithAverage(
                userHealthAverage.getDailyMealPatternAverage(),
                userHealthAverage.getDailyMealRegularityAverage(),
                userHealthAverage.getDailyMealNutritionIntakeAverage(),
                userHealthAverage.getDailyMealAlcoholFrequencyAverage(),
                gptResult.getMealPatternToday()
        );
        SleepPatternResponseWithAverage sleepPatternResponseWithAverage = new SleepPatternResponseWithAverage(
                userHealthAverage.getDailySleepPatternAverage(),
                userHealthAverage.getDailySleepRegularityAverage(),
                userHealthAverage.getDailySleepQualityAverage(),
                userHealthAverage.getDailySleepFocusAverage(),
                gptResult.getSleepPatternToday()
        );
        return new DiagnosisDayResponseDTO(formatDate,lifeStyleResponseWithAverage,mealPatternResponseWithAverage,sleepPatternResponseWithAverage);
    }
    public DiagnosisWeekResponseDTO findWeekDiagnosisResult(Long userId, String date) {
        LocalDate formatDate = LocalDate.parse(date, FORMATTER);
        LocalDate previousWeekMonday = formatDate.minusWeeks(1).with(DayOfWeek.MONDAY);
        LocalDate previousWeekSunday = formatDate.minusWeeks(1).with(DayOfWeek.SUNDAY);

        GptWeekResult gptWeekResult = gptWeekResultRepository.findDiagnosisResultByUserIdAndDate(userId, (long)formatDate.get(WeekFields.ISO.weekOfYear()),(long)formatDate.getYear())
                .orElseThrow(() -> new DiagnosisException(ExceptionResponseStatus.INVALID_DIAGNOSIS_VALUE, "진단 결과가 존재하지 않습니다"));
        List<UserHealthAverage> userHealthAverages = userHealthAverageService.getAverageByDate(previousWeekMonday,previousWeekSunday);

        //사용자 평균 점수 가져오기
        List<Double> lifeAverages = new ArrayList<>();
        List<Double> mealAverages = new ArrayList<>();
        List<Double> sleepAverages = new ArrayList<>();
        for (UserHealthAverage item :userHealthAverages){
            lifeAverages.add(item.getWeeklyLifestyleAverage());
            mealAverages.add(item.getWeeklyMealPatternAverage());
            sleepAverages.add(item.getWeeklySleepPatternAverage());
        }

        //내 점수 가져오기
        List<Integer> lifeScores = new ArrayList<>();
        List<Integer> mealScores = new ArrayList<>();
        List<Integer> sleepScores = new ArrayList<>();
        List<GptResult> gptResults = gptResultRepository.findDiagnosisResultByUserIdAndBetweenDate(userId,previousWeekMonday,previousWeekSunday);
        for (GptResult item :gptResults){
            lifeScores.add(item.getLifeStyleToday().getLifeStyleScore());
            mealScores.add(item.getMealPatternToday().getDailyMealPatternScore());
            sleepScores.add(item.getSleepPatternToday().getDailySleepPatternScore());
        }
        LifeStyleWeekResponse lifeStyleWeekResponse = new LifeStyleWeekResponse(
                lifeAverages,lifeScores,gptWeekResult.getLifeStyleToday().getDescription(), gptWeekResult.getLifeStyleToday().getRiskScore(),gptWeekResult.getLifeStyleToday().getRiskSymptoms(),gptWeekResult.getLifeStyleToday().getChallenges()
        );
        MealPatternWeekResponse mealPatternWeekResponse = new MealPatternWeekResponse(
               mealAverages,mealScores,gptWeekResult.getMealPatternToday().getDescription(), gptWeekResult.getMealPatternToday().getRiskScore(),gptWeekResult.getMealPatternToday().getRiskSymptoms(),gptWeekResult.getMealPatternToday().getChallenges()
        );
        SleepPatternWeekResponse sleepPatternWeekResponse = new SleepPatternWeekResponse(
                sleepAverages,sleepScores,gptWeekResult.getSleepPatternToday().getDescription(), gptWeekResult.getSleepPatternToday().getRiskScore(),gptWeekResult.getSleepPatternToday().getRiskSymptoms(),gptWeekResult.getSleepPatternToday().getChallenges()
        );
        return new DiagnosisWeekResponseDTO(formatDate,lifeStyleWeekResponse,mealPatternWeekResponse,sleepPatternWeekResponse);
    }
    public DiagnosisMonthResponseDTO findMonthDiagnosisResult(Long userId, String date) {
        LocalDate formatDate = LocalDate.parse(date, FORMATTER);
        GptMonthResult gptMonthResult = gptMonthResultRepository.findDiagnosisResultByUserIdAndDate(userId, (long)formatDate.getMonthValue(),(long)formatDate.getYear())
                .orElseThrow(() -> new DiagnosisException(ExceptionResponseStatus.INVALID_DIAGNOSIS_VALUE, "진단 결과가 존재하지 않습니다"));
        //월로 조회
        //내 점수 가져오기
        List<Integer> lifeScores = new ArrayList<>();
        List<Integer> mealScores = new ArrayList<>();
        List<Integer> sleepScores = new ArrayList<>();
        List<GptWeekResult> userWeekResults = gptWeekResultRepository.findAllByUserIdAndYearAndMonth(userId,(long)formatDate.getMonthValue() - 1,(long) formatDate.getYear());
        for (GptWeekResult item :userWeekResults){
            lifeScores.add(item.getLifeStyleToday().getLifeStyleScore());
            mealScores.add(item.getMealPatternToday().getDailyMealPatternScore());
            sleepScores.add(item.getSleepPatternToday().getDailySleepPatternScore());
        }
        LocalDate previousMonthFirstDay = formatDate.minusMonths(1).withDayOfMonth(1);
        List<Double> lifeAverages = new ArrayList<>();
        List<Double> mealAverages = new ArrayList<>();
        List<Double> sleepAverages = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            previousMonthFirstDay = previousMonthFirstDay.plusDays(7);
            List<UserHealthAverage> userHealthAverages = userHealthAverageService.getAverageByDate(previousMonthFirstDay,previousMonthFirstDay);
            for (UserHealthAverage item :userHealthAverages){
                lifeAverages.add(item.getWeeklyLifestyleAverage());
                mealAverages.add(item.getWeeklyMealPatternAverage());
                sleepAverages.add(item.getWeeklySleepPatternAverage());
            }
        }
        LifeStyleWeekResponse lifeStyleWeekResponse = new LifeStyleWeekResponse(
                lifeAverages,lifeScores,gptMonthResult.getLifeStyleToday().getDescription(), gptMonthResult.getLifeStyleToday().getRiskScore(),gptMonthResult.getLifeStyleToday().getRiskSymptoms(),gptMonthResult.getLifeStyleToday().getChallenges()
        );
        MealPatternWeekResponse mealPatternWeekResponse = new MealPatternWeekResponse(
                mealAverages,mealScores,gptMonthResult.getMealPatternToday().getDescription(), gptMonthResult.getMealPatternToday().getRiskScore(),gptMonthResult.getMealPatternToday().getRiskSymptoms(),gptMonthResult.getMealPatternToday().getChallenges()
        );
        SleepPatternWeekResponse sleepPatternWeekResponse = new SleepPatternWeekResponse(
                sleepAverages,sleepScores,gptMonthResult.getSleepPatternToday().getDescription(), gptMonthResult.getSleepPatternToday().getRiskScore(),gptMonthResult.getSleepPatternToday().getRiskSymptoms(),gptMonthResult.getSleepPatternToday().getChallenges()
        );
        return new DiagnosisMonthResponseDTO(formatDate,lifeStyleWeekResponse,mealPatternWeekResponse,sleepPatternWeekResponse);
    }

}
