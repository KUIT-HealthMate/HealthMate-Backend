package com.kuit.healthmate.challenge.habit.controller;


import com.kuit.healthmate.auth.jwt.Jwt;
import com.kuit.healthmate.challenge.habit.domain.Habit;
import com.kuit.healthmate.challenge.habit.dto.request.GetHabitRequest;
import com.kuit.healthmate.challenge.habit.dto.request.PatchEditHabitRequest;
import com.kuit.healthmate.challenge.habit.dto.request.PostCreateHabitRequest;
import com.kuit.healthmate.challenge.habit.dto.request.PutCheckHabitRequest;
import com.kuit.healthmate.challenge.habit.dto.response.HabitEditResponse;
import com.kuit.healthmate.challenge.habit.dto.response.PostCreateHabitResponse;

import com.kuit.healthmate.global.exception.HabitException;
import com.kuit.healthmate.global.response.ApiResponse;
import com.kuit.healthmate.challenge.habit.service.HabitService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.kuit.healthmate.global.response.ExceptionResponseStatus.INVALID_HABIT_VALUE;
import static com.kuit.healthmate.utils.BindingResultUtils.getErrorMessages;

@Tag(name = "habits", description = "습관 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@OpenAPIDefinition(
        security = {@SecurityRequirement(name = "bearerAuth")}
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@RequestMapping("/habits")
public class habitController {
    private final HabitService habitService;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    /**
     * 습관 챌린지 조회
     */
    @Deprecated
    @GetMapping("/{userId}")
    public ApiResponse<List<Habit>> findHabitChallenge(@RequestBody GetHabitRequest getHabitRequest , @PathVariable long userId) {
        return new ApiResponse<>(habitService.getActiveHabitsByUserIdAndToday(userId,LocalDate.parse(getHabitRequest.getDate(), FORMATTER)));
    }
    /**
     * 습관 챌린지 생성
     */
    @Operation(
            summary = "습관 생성 API"
    )
    @PostMapping("")
    public ApiResponse<Long> createHabitChallenge(@Validated @RequestBody PostCreateHabitRequest postCreateHabitRequest, BindingResult bindingResult,@Jwt Long userId){
        if (bindingResult.hasErrors()) {
            throw new HabitException(INVALID_HABIT_VALUE, getErrorMessages(bindingResult));
        }
        return new ApiResponse<>(habitService.createHabit(postCreateHabitRequest, userId));
    }
    /**
     * 습관 챌린지 수정
     */
    @PutMapping("/edit/{habitId}")
    public ApiResponse<Object> updateHabitChallenge(@PathVariable Long habitId, @Validated @RequestBody PatchEditHabitRequest patchEditHabitRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new HabitException(INVALID_HABIT_VALUE, getErrorMessages(bindingResult));
        }
        habitService.updateHabit(habitId, patchEditHabitRequest);
        return new ApiResponse<>(null);
    }
    /**
     * 습관 챌린지 삭제
     */
    @PutMapping("/delete/{habitId}")
    public ApiResponse<Object> updateHabitStatus(@PathVariable Long habitId){
         habitService.deleteHabit(habitId);
        return new ApiResponse<>(null);
    }
    /**
     * 습관 챌린지 체크/언체크
     */
    @PutMapping("/check-status/{habitId}")
    public ApiResponse<Boolean> checkHabitChecker(@PathVariable Long habitId,@RequestBody PutCheckHabitRequest putCheckHabitRequest, BindingResult bindingResult) {
        habitService.checkHabit(putCheckHabitRequest.getDate(), habitId);
        return new ApiResponse<>(null);
    }
    @GetMapping("/edit")
    public ApiResponse<List<HabitEditResponse>> editHabitBase(@Jwt Long userId) {
        List<Habit> habitList = habitService.getSupplementEditList(userId);
        List<HabitEditResponse> habitEditResponses = habitList.stream()
                .map(HabitEditResponse::new)
                .toList();
        return new ApiResponse<>(habitEditResponses);
    }
}
