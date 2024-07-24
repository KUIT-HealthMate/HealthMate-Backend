package com.kuit.healthmate.habit;


import com.kuit.healthmate.challenge.habit.domain.Habit;
import com.kuit.healthmate.challenge.habit.domain.HabitChecker;
import com.kuit.healthmate.challenge.habit.domain.HabitStatus;
import com.kuit.healthmate.challenge.habit.repository.HabitCheckerRepository;
import com.kuit.healthmate.challenge.habit.repository.HabitRepository;
import com.kuit.healthmate.challenge.habit.service.HabitService;
import com.kuit.healthmate.challenge.supplement.repository.UserRepository;
import com.kuit.healthmate.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
public class HabitTest {
    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    HabitService habitService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    HabitCheckerRepository habitCheckerRepository;

    @PersistenceContext
    EntityManager em;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

//    @Test
//    @DisplayName("습관 조인 테스트")
//    @Transactional
//    void 습관조인테스트(){
//        Habit habit1 = Habit.builder()
//                .id(1L)
//                .name("test")
//                .memo("testtest")
//                .status("ACTIVE")
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .selectedDay("1101101").build();
//        Habit habit2 = Habit.builder()
//                .id(2L)
//                .name("test2")
//                .memo("testtest2")
//                .status("ACTIVE")
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .selectedDay("1101101").build();
//        habitRepository.save(habit1);
//        habitRepository.save(habit2);
//
//        HabitChecker habitChecker1 = HabitChecker.builder()
//                .habit(habit1)
//                .createdAt(LocalDateTime.now())
//                .status(Boolean.TRUE).build();
//
//        HabitChecker habitChecker2 = HabitChecker.builder()
//                .habit(habit2)
//                .createdAt(LocalDateTime.now())
//                .status(Boolean.TRUE).build();
//
//        habitCheckerRepository.save(habitChecker1);
//        habitCheckerRepository.save(habitChecker2);
//
//        em.flush();
//        em.clear();
//
//        List<Habit> habits = habitRepository.findByIdWithHabitCheckers(2L);
//        habits.forEach(h -> System.out.println("결과 : "+h.getHabitChecker()));
//    }
    @Test
    @DisplayName("습관 조회 테스트")
    @Transactional
    void 습관조회테스트(){

        User user = User.builder()
                .id(1L)
                .nickname("park")
                .email("test").createdAt(LocalDateTime.now()).build();
        userRepository.save(user);
        Habit habit1 = Habit.builder()
                .name("월")
                .memo("testtest")
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(user)
                .selectedDay("1000000").build();
        System.out.println("result :" +habit1.toString());
        Habit habit2 = Habit.builder()
                .name("화")
                .memo("testtest2")
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(user)
                .selectedDay("0100000").build();
        Habit habit3 = Habit.builder()
                .name("월화")
                .memo("testtest2")
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(user)
                .selectedDay("1100000").build();
        Habit habit4 = Habit.builder()
                .name("전체")
                .memo("testtest2")
                .status(HabitStatus.ACTIVE.toString())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(user)
                .selectedDay("1111111").build();
        habitRepository.save(habit1);
        habitRepository.save(habit2);
        habitRepository.save(habit3);
        habitRepository.save(habit4);
        em.flush();
        em.clear();
        LocalDate date = LocalDate.parse("2024.07.22", FORMATTER);
        int dayOfWeek = date.getDayOfWeek().getValue();
        List<Habit> habits = habitService.getActiveHabitsByUserIdAndToday(1L,date);
    }
}
