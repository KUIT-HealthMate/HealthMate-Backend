package com.kuit.healthmate.challenge.common.service;

import com.kuit.healthmate.challenge.common.dto.response.ChallengeByPeriodResponse;
import com.kuit.healthmate.challenge.common.dto.response.ChallengeResponse;
import com.kuit.healthmate.challenge.common.dto.response.HabitItem;
import com.kuit.healthmate.challenge.common.dto.response.SupplementItem;
import com.kuit.healthmate.challenge.habit.domain.Habit;
import com.kuit.healthmate.challenge.habit.domain.HabitChecker;
import com.kuit.healthmate.challenge.habit.service.HabitService;
import com.kuit.healthmate.challenge.supplement.domain.Supplement;
import com.kuit.healthmate.challenge.supplement.domain.SupplementChecker;
import com.kuit.healthmate.challenge.supplement.service.SupplementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.round;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommonChallengeServiceImpl implements CommonChallengeService{
    private final HabitService habitService;
    private final SupplementService supplementService;

    /*
     * 성취도
     * 영양제 챌린지(아점저, 성공 유무)
     * 습관 챌린지(성공유무)
     * */
    public ChallengeResponse getChallengesForDay(Long userId, LocalDate localDate){
        double achievementRate = 0;
        int totalRequired = 0;
        int totalSuccess = 0;

        // 영양제 챌린지 정보를 가져옴
        List<Supplement> supplements = supplementService.getSupplementForDay(userId, localDate);

        // habit 챌린지 정보 가져옴
        List<Habit> habits = habitService.getActiveHabitsByUserIdAndToday(userId, localDate);

        // 각 필드로 생성
        List<SupplementItem> supplementItems = supplements.stream().map((Supplement supplement) ->
                SupplementItem.fromSupplement(supplement,localDate)).toList();

        List<HabitItem> habitItems = habits.stream().map((Habit habit) ->
            HabitItem.fromHabit(habit,localDate)).toList();


        achievementRate = calculateAchievementRate(supplementItems, habitItems);

        ChallengeResponse challengeResponse = new ChallengeResponse(habitItems,supplementItems,achievementRate,
                localDate.toString());

        return challengeResponse;
    }


    /*
     * 성취도
     * 영양제 챌린지(아점저, 성공 유무)
     * 습관 챌린지(성공유무)
     * */
    public ChallengeResponse getChallengesForToday(Long userId){
        double achievementRate = 0;
        int totalRequired = 0;
        int totalSuccess = 0;

        LocalDate todayDate = LocalDate.parse(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        // 영양제 챌린지 정보를 가져옴
        List<Supplement> supplements = supplementService.getSupplementForToday(userId);

        // 각 필드로 생성
        List<SupplementItem> supplementItems = supplements.stream().map((Supplement supplement) ->
                SupplementItem.fromSupplement(supplement,todayDate)).toList();

        List<Habit> habits = habitService.getActiveHabitsByUserIdAndToday(userId, todayDate);

        List<HabitItem> habitItems = habits.stream().map((Habit habit) ->
                HabitItem.fromHabit(habit,todayDate)).toList();

        achievementRate = calculateAchievementRate(supplementItems, habitItems);

        // ChallengeResponse 객체를 생성하고 반환하기
        ChallengeResponse challengeResponse = new ChallengeResponse(habitItems,supplementItems,achievementRate,
                todayDate.toString());

        return challengeResponse;
    }


    /*
     * today 정보 + 요일별 달성율 + 주간 달성율
     * */
    public ChallengeByPeriodResponse getChallengesForWeek(Long userId, LocalDate startDate, LocalDate endDate) {
        double achievementRate = 0;
        double totalAchievement = 0;

        List<Supplement> supplements = supplementService.getSupplementForWeek(userId,startDate,endDate);
        List<Habit> habits = habitService.getHabitForWeek(userId, startDate, endDate);

        Map<LocalDate, List<SupplementItem>> supplementsByDate = new HashMap<>();
        Map<LocalDate, List<HabitItem>> habitsByDate = new HashMap<>();

        for (Supplement supplement : supplements) {
            for (SupplementChecker checker : supplement.getSupplementCheckers()) {
                LocalDate checkDate = checker.getCheckDate();
                supplementsByDate
                        .computeIfAbsent(checkDate, k -> new ArrayList<>())
                        .add(SupplementItem.fromSupplement(supplement,checkDate));
            }
        }

        for (Habit habit : habits) {
            for (HabitChecker habitChecker : habit.getHabitChecker()) {
                LocalDate checkDate = habitChecker.getCreatedAt();
                habitsByDate
                        .computeIfAbsent(checkDate, k -> new ArrayList<>())
                        .add(HabitItem.fromHabit(habit,checkDate));
            }
        }


        List<ChallengeResponse> challengeResponses = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            List<SupplementItem> supplementItems = supplementsByDate.getOrDefault(date, new ArrayList<>());
            List<HabitItem> habitItems = habitsByDate.getOrDefault(date, new ArrayList<>());

            achievementRate = calculateAchievementRate(supplementItems, habitItems);
            totalAchievement += achievementRate;

            ChallengeResponse challengeResponse = new ChallengeResponse(habitItems, supplementItems, achievementRate,
                    date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            challengeResponses.add(challengeResponse);
        }

        totalAchievement = round(totalAchievement / (ChronoUnit.DAYS.between(startDate, endDate)+1));

        return new ChallengeByPeriodResponse(challengeResponses,totalAchievement);
    }


    /*
     * today 정보 + 요일별 달성율 + 월 달성율
     * */
    public ChallengeByPeriodResponse getChallengesForMonth(Long userId, LocalDate startDate, LocalDate endDate) {
        double achievementRate = 0;
        double totalAchievement = 0;

        List<Supplement> supplements = supplementService.getSupplementForWeek(userId,startDate,endDate);
        List<Habit> habits = habitService.getHabitForWeek(userId, startDate, endDate);

        Map<LocalDate, List<SupplementItem>> supplementsByDate = new HashMap<>();
        Map<LocalDate, List<HabitItem>> habitsByDate = new HashMap<>();

        for (Supplement supplement : supplements) {
            for (SupplementChecker checker : supplement.getSupplementCheckers()) {
                LocalDate checkDate = checker.getCheckDate();
                supplementsByDate
                        .computeIfAbsent(checkDate, k -> new ArrayList<>())
                        .add(SupplementItem.fromSupplement(supplement,checkDate));
            }
        }

        for (Habit habit : habits) {
            for (HabitChecker habitChecker : habit.getHabitChecker()) {
                LocalDate checkDate = habitChecker.getCreatedAt();
                habitsByDate
                        .computeIfAbsent(checkDate, k -> new ArrayList<>())
                        .add(HabitItem.fromHabit(habit,checkDate));
            }
        }


        List<ChallengeResponse> challengeResponses = new ArrayList<>();
        for (LocalDate date = startDate.withDayOfMonth(1); !date.isAfter(endDate); date = date.plusDays(1)) {
            List<SupplementItem> supplementItems = supplementsByDate.getOrDefault(date, new ArrayList<>());
            List<HabitItem> habitItems = habitsByDate.getOrDefault(date, new ArrayList<>());

            achievementRate = calculateAchievementRate(supplementItems, habitItems);

            ChallengeResponse challengeResponse = new ChallengeResponse(habitItems, supplementItems, achievementRate,
                    date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            challengeResponses.add(challengeResponse);
        }

        totalAchievement = round(totalAchievement / (ChronoUnit.DAYS.between(startDate, endDate)+1));

        return new ChallengeByPeriodResponse(challengeResponses,totalAchievement);
    }

    private double calculateAchievementRate(List<SupplementItem> supplementItems, List<HabitItem> habitItems) {
        int totalRequired = 0;
        int totalSuccess = 0;

        for (SupplementItem supplementItem : supplementItems) {
            totalRequired++;
            if (supplementItem.getSuccess()) totalSuccess++;
        }

        for (HabitItem habitItem : habitItems) {
            totalRequired++;
            if (habitItem.getSuccess()) totalSuccess++;
        }

        return totalRequired > 0 ? (double) round(totalSuccess / totalRequired * 100) : 0;
    }



}
