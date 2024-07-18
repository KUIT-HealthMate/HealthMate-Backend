package com.kuit.healthmate.Controller;


import com.kuit.healthmate.domain.habit.entity.Habit;
import com.kuit.healthmate.service.HabitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public List<Habit> findHabitChallenge(@PathVariable long userId) {
        return habitService.getActiveHabitsByUserIdAndToday(userId);
    }
}
