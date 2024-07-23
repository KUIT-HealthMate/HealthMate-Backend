package com.kuit.healthmate.challenge.common.service;

import com.kuit.healthmate.challenge.common.dto.ChallengeResponse;

import java.time.DayOfWeek;
import java.time.LocalDate;

public interface CommonChallengeService {
    /*
     * 해당 요일 챌린지 가져오기
     * */
    public ChallengeResponse getChallengesForDay(LocalDate localDate);

    /*
     * 금일 챌린지 가져오기
     * */
    public ChallengeResponse getChallengesForToday();
}
