package com.kuit.healthmate.challenge.common.service;

import com.kuit.healthmate.challenge.common.dto.response.ChallengeByPeriodResponse;
import com.kuit.healthmate.challenge.common.dto.response.ChallengeResponse;

import java.time.LocalDate;

public interface CommonChallengeService {
    /*
     * 해당 요일 챌린지 가져오기(챌린지 리스트와 수행여부)
     * */
    public ChallengeResponse getChallengesForDay(Long userId,LocalDate localDate);

    /*
     * 금일 챌린지 가져오기
     * */
    public ChallengeResponse getChallengesForToday(Long userId);

    /*
    *  주간 챌린지 가져오기(챌린지 리스트와 수행 여부, 성취도)
    * */
    public ChallengeByPeriodResponse getChallengesForMonth(Long userId, LocalDate startDate, LocalDate endDate);

    /*
    * 월간 챌린지 가져오기(챌린지 리스트와 수행 여부, 성취도)
    * */
    public ChallengeByPeriodResponse getChallengesForWeek(Long userId, LocalDate startDate, LocalDate endDate);
}
