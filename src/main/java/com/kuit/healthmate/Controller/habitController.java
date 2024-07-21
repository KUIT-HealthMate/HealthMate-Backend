package com.kuit.healthmate.Controller;


import com.kuit.healthmate.domain.habit.entity.Habit;
import com.kuit.healthmate.dto.habit.PostCreateHabitRequest;
import com.kuit.healthmate.global.exception.HabitException;
import com.kuit.healthmate.global.response.ApiResponse;
import com.kuit.healthmate.service.HabitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kuit.healthmate.global.response.ExceptionResponseStatus.INVALID_HABIT_VALUE;
import static com.kuit.healthmate.utils.BindingResultUtils.getErrorMessages;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/habits")
public class habitController {
    private final HabitService habitService;
    /**
     * 습관 챌린지 조회
     */
    @GetMapping("/{userId}")
    public ApiResponse<List<Habit>> findHabitChallenge(@PathVariable long userId) {
        return new ApiResponse<>(habitService.getActiveHabitsByUserIdAndToday(userId));
    }
    /**
     * 습관 챌린지 생성
     */
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
//    @PatchMapping("/edit")
//    public void updateHabitChallenge(@RequestBody Habit habit){
//        habitService.updateHabit(habit,times);
    //    return;
//    }
    /**
     * 습관 챌린지 삭제
     */
    @PatchMapping("/delete/{habitId}")
    public void updateHabitStatus(@PathVariable Long habitId){
         habitService.deleteHabit(habitId);
         return;
    }
}
