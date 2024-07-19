package com.kuit.healthmate.Controller;


import com.kuit.healthmate.domain.habit.entity.Habit;
import com.kuit.healthmate.service.HabitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    /**
     * 습관 챌린지 생성
     */
//    @PostMapping("")
//    public void createHabitChallenge(@RequestBody Habit habit){ //response 클래스 생성 해야함,, 임시로 request body로 habit -> 사용자의 정보, 습관 수행 시간 등을 포함한 객체로 교체해야함
//        habitService.createHabit(habit,);
//        return;
//    }
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
