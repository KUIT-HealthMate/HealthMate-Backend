package com.kuit.healthmate.service;

import com.kuit.healthmate.domain.habit.entity.Habit;
import com.kuit.healthmate.domain.habit.repository.HabitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HabitService {

    private final HabitRepository habitRepository;

    @Transactional
    public Habit registerHabit(Habit habit){
        return habitRepository.save(habit);
    }

    @Transactional
    public List<Habit> getActiveHabitsByUserIdAndToday(Long userId) {
        int dayOfWeek = LocalDateTime.now().getDayOfWeek().getValue();
        // 요일을 월요일부터 시작하는 1부터 7까지의 값으로 맞추기 위해 필요
        dayOfWeek = (dayOfWeek % 7) + 1; // 월요일이 1, 화요일이 2, ..., 일요일이 7

        return habitRepository.findActiveHabitsByUserIdAndDayOfWeek(userId, dayOfWeek);
    }
}
