package com.kuit.healthmate.habit;


import com.kuit.healthmate.challenge.habit.domain.Habit;
import com.kuit.healthmate.challenge.habit.domain.HabitChecker;
import com.kuit.healthmate.challenge.habit.repository.HabitCheckerRepository;
import com.kuit.healthmate.challenge.habit.repository.HabitRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
public class HabitTest {
    @Autowired
    private HabitRepository habitRepository;


    @Autowired
    HabitCheckerRepository habitCheckerRepository;

    @PersistenceContext
    EntityManager em;



    @Test
    @DisplayName("습관 조인 테스트")
    @Transactional
    void 습관조인테스트(){
        Habit habit1 = Habit.builder()
                .id(1L)
                .name("test")
                .memo("testtest")
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .selectedDay("1101101").build();
        Habit habit2 = Habit.builder()
                .id(2L)
                .name("test2")
                .memo("testtest2")
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .selectedDay("1101101").build();
        habitRepository.save(habit1);
        habitRepository.save(habit2);

        HabitChecker habitChecker1 = HabitChecker.builder()
                .habit(habit1)
                .createdAt(LocalDateTime.now())
                .status(Boolean.TRUE).build();

        HabitChecker habitChecker2 = HabitChecker.builder()
                .habit(habit2)
                .createdAt(LocalDateTime.now())
                .status(Boolean.TRUE).build();

        habitCheckerRepository.save(habitChecker1);
        habitCheckerRepository.save(habitChecker2);

        em.flush();
        em.clear();

        List<Habit> habits = habitRepository.findByIdWithHabitCheckers(2L);
        habits.forEach(h -> System.out.println("결과 : "+h.getHabitChecker()));
    }

}
