package com.kuit.healthmate.challenge.habit.controller;


import com.kuit.healthmate.challenge.habit.domain.Habit;
import com.kuit.healthmate.challenge.habit.dto.*;
import com.kuit.healthmate.global.exception.HabitException;
import com.kuit.healthmate.global.response.ApiResponse;
import com.kuit.healthmate.challenge.habit.service.HabitService;
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
    @Deprecated
    @PostMapping("")
    public ApiResponse<Habit> createHabitChallenge(@Validated @RequestBody PostCreateHabitRequest postCreateHabitRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new HabitException(INVALID_HABIT_VALUE, getErrorMessages(bindingResult));
        }
        return new ApiResponse<>(habitService.createHabit(postCreateHabitRequest));
    }
    /**
     * 습관 챌린지 수정
     */
    @Deprecated
    @PatchMapping("/edit")
    public ApiResponse<Object> updateHabitChallenge(@Validated @RequestBody PatchEditHabitRequest patchEditHabitRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new HabitException(INVALID_HABIT_VALUE, getErrorMessages(bindingResult));
        }
        habitService.updateHabit(patchEditHabitRequest);
        return new ApiResponse<>(null);
    }
    /**
     * 습관 챌린지 삭제
     */
    @Deprecated
    @PatchMapping("/delete/{habitId}")
    public ApiResponse<Object> updateHabitStatus(@PathVariable Long habitId){
         habitService.deleteHabit(habitId);
        return new ApiResponse<>(null);
    }
    /**
     * 습관 챌린지 체크/언체크
     */
    @Deprecated
    @PutMapping("/check-status/{habitId}")
    public ApiResponse<Boolean> checkHabitChecker(@RequestBody PutCheckHabitRequest putCheckHabitRequest,BindingResult bindingResult, @PathVariable Long habitId) {
        habitService.checkHabit(putCheckHabitRequest.getDate(), habitId);
        return new ApiResponse<>(null);
    }
}
