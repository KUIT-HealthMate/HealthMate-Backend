package com.kuit.healthmate.service;

import com.kuit.healthmate.domain.habit.entity.Habit;
import com.kuit.healthmate.domain.habit.repository.HabitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HabitService {

    private final HabitRepository habitRepository;

    @Transactional
    public Habit registerHabit(Habit habit){
        return habitRepository.save(habit);
    }
}
