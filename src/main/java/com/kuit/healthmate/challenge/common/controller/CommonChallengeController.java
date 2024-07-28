package com.kuit.healthmate.challenge.common.controller;

import com.kuit.healthmate.challenge.common.dto.request.ChallengeByTodayRequest;
import com.kuit.healthmate.challenge.common.dto.response.ChallengeByPeriodResponse;
import com.kuit.healthmate.challenge.common.dto.request.ChallengeRequest;
import com.kuit.healthmate.challenge.common.dto.response.ChallengeResponse;
import com.kuit.healthmate.challenge.common.service.CommonChallengeService;
import com.kuit.healthmate.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/challenges")
@Validated
public class CommonChallengeController {
    private final CommonChallengeService commonChallengeService;
    // 불변 객체 계속 사용할 변수임.
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 특정 날짜의 챌린지를 가져옵니다.
     *
     * @return 해당 날짜의 챌린지 정보
     */
    @GetMapping("/by-day")
    public ApiResponse<ChallengeResponse> getChallengesByDay(@RequestBody @Valid ChallengeByTodayRequest challenge) {
        LocalDate date = LocalDate.parse(challenge.getDate(), FORMATTER);
        return new ApiResponse<>(commonChallengeService.getChallengesForDay(challenge.getUserId(), date));
    }

    /**
     * 오늘의 챌린지를 가져옵니다.
     * @return 금일의 챌린지 정보
     */
    @GetMapping("/today/{userId}")
    public ApiResponse<ChallengeResponse> getChallengesByToday(@PathVariable Long userId) {
        log.info("");
        return new ApiResponse<>(commonChallengeService.getChallengesForToday(userId));
    }

    /**
     * 금주의 챌린지를 가져옵니다.
     * @return 금일의 챌린지 정보
     */
    @GetMapping("/week")
    public ApiResponse<ChallengeByPeriodResponse> getChallengesByWeek(@RequestBody @Valid ChallengeRequest challengeRequest) {
        LocalDate dateStart = LocalDate.parse(challengeRequest.getStartDate(), FORMATTER);
        LocalDate dateEnd = LocalDate.parse(challengeRequest.getEndDate(), FORMATTER);

        return new ApiResponse<>(commonChallengeService.getChallengesForWeek(challengeRequest.getUserId(),
                dateStart, dateEnd));
    }

    /**
     * 이번 달의 챌린지를 가져옵니다.
     * @return 금일의 챌린지 정보
     */
    @GetMapping("/month")
    public ApiResponse<ChallengeByPeriodResponse> getChallengesByMonth(@RequestBody @Valid ChallengeRequest challengeRequest) {
        LocalDate dateStart = LocalDate.parse(challengeRequest.getStartDate(), FORMATTER);
        LocalDate dateEnd = LocalDate.parse(challengeRequest.getEndDate(), FORMATTER);
        return new ApiResponse<>(commonChallengeService.getChallengesForMonth(challengeRequest.getUserId(),
                dateStart, dateEnd));
    }

}
