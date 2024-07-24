package com.kuit.healthmate.challenge.common.controller;

import com.kuit.healthmate.challenge.common.dto.ChallengeResponse;
import com.kuit.healthmate.challenge.common.service.CommonChallengeService;
import com.kuit.healthmate.challenge.common.service.CommonChallengeServiceImpl;
import com.kuit.healthmate.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/challenges")
public class CommonChallengeController {
    private final CommonChallengeService commonChallengeService;
    // 불변 객체 계속 사용할 변수임.
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    /**
     * 특정 날짜의 챌린지를 가져옵니다.
     *
     * @param date 조회할 날짜 (예: 2024.02.25)
     * @return 해당 날짜의 챌린지 정보
     */
    @GetMapping("/by-day")
    public ApiResponse<ChallengeResponse> getChallengesByDay(@RequestParam String date) {
        // userId는?
        log.info(date);
        return new ApiResponse<>(commonChallengeService.getChallengesForDay(LocalDate.parse(date, FORMATTER)));
    }

    /**
     * 금일의 챌린지를 가져옵니다.
     * @return 금일의 챌린지 정보
     */
    @GetMapping("/today")
    public ApiResponse<ChallengeResponse> getTodayChallenges() {
        log.info("");
        return new ApiResponse<>(commonChallengeService.getChallengesForToday());
    }

}