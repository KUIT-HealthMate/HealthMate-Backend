package com.kuit.healthmate.service;

import com.kuit.healthmate.domain.Habit;
import com.kuit.healthmate.repository.HabitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HabitService {

    private final HabitRepository habitRepository;

//    @Transactional
//    public Long registerHabit(Habit habit){
//        return habitRepository.save(habit).getId();
//    }
}
