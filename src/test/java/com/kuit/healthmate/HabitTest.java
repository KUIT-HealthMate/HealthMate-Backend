package com.kuit.healthmate;


import com.kuit.healthmate.domain.habit.entity.Habit;
import com.kuit.healthmate.domain.habit.repository.HabitRepository;
import com.kuit.healthmate.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@DataJpaTest
public class HabitTest {
    @Autowired
    private HabitRepository habitRepository;


//    @Test
//    @DisplayName("습관 정보 저장 테스트")
//    @Transactional
//    void 습관정보저장테스트(){
//        Habit habit = Habit.builder()
//                .id(1L)
//                .name("test")
//                .memo("testtest")
//                .status("ACTIVE")
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .selected_day("1101101").build();
//
//
//
//        Habit newHabit = habitRepository.save(habit);
//        Assertions.assertNotNull(newHabit);
//    }
//
//    @Test
//    @DisplayName("사용자ID로 습관 리스트 반환")
//    @Transactional
//    void 사용자ID로습관리스트반환(){
//
//    }
}
