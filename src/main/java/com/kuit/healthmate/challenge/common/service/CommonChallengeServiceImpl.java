package com.kuit.healthmate.challenge.common.service;

import com.kuit.healthmate.challenge.common.dto.ChallengeResponse;
import com.kuit.healthmate.challenge.common.dto.HabitItem;
import com.kuit.healthmate.challenge.common.dto.SupplementItem;
import com.kuit.healthmate.challenge.habit.service.HabitService;
import com.kuit.healthmate.challenge.supplement.service.SupplementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonChallengeServiceImpl implements CommonChallengeService{
    private final HabitService habitService;
    private final SupplementService supplementService;

    public ChallengeResponse getChallengesForDay(LocalDate localDate){
        return null;
    }


    public ChallengeResponse getChallengesForToday(){
        return null;
    }

    /*
     * 달성률 계산 로직
     * */
    private int calculateAchievementRate(List<HabitItem> habits, List<SupplementItem> supplements) {
        // ex
        return 80;
    }
}
