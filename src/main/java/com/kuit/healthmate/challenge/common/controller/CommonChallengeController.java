package com.kuit.healthmate.challenge.common.controller;

import com.kuit.healthmate.auth.jwt.Jwt;
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
    @Deprecated
    @GetMapping("/by-day")
    // @Jwt Long userId , requestParam 또는 쿼리 스트링
    public ApiResponse<ChallengeResponse> getChallengesByDay(@Jwt Long userId, @RequestParam("date") String dateString) {
        LocalDate date = LocalDate.parse(dateString, FORMATTER);
        return new ApiResponse<>(commonChallengeService.getChallengesForDay(userId, date));
    }

    /**
     * 오늘의 챌린지를 가져옵니다.
     * @return 금일의 챌린지 정보
     */
    @Deprecated
    @GetMapping("/today")
    public ApiResponse<ChallengeResponse> getChallengesByToday(@Jwt Long userId) {
        log.info("");
        return new ApiResponse<>(commonChallengeService.getChallengesForToday(userId));
    }

    /**
     * 금주의 챌린지를 가져옵니다.
     * @return 금일의 챌린지 정보
     */
    @Deprecated
    @GetMapping("/week")
    public ApiResponse<ChallengeByPeriodResponse> getChallengesByWeek(@Jwt Long userId,
                                                                      @RequestParam("startDate") String startDate,
                                                                      @RequestParam("endDate") String endDate) {
        LocalDate dateStart = LocalDate.parse(startDate, FORMATTER);
        LocalDate dateEnd = LocalDate.parse(endDate, FORMATTER);

        return new ApiResponse<>(commonChallengeService.getChallengesForWeek(userId, dateStart, dateEnd));
    }

    /**
     * 이번 달의 챌린지를 가져옵니다.
     * @return 금일의 챌린지 정보
     */
    @Deprecated
    @GetMapping("/month")
    public ApiResponse<ChallengeByPeriodResponse> getChallengesByMonth(@Jwt Long userId,
                                                                       @RequestParam("startDate") String startDate,
                                                                       @RequestParam("endDate") String endDate) {
        LocalDate dateStart = LocalDate.parse(startDate, FORMATTER);
        LocalDate dateEnd = LocalDate.parse(endDate, FORMATTER);
        return new ApiResponse<>(commonChallengeService.getChallengesForMonth(userId, dateStart, dateEnd));
    }

}
