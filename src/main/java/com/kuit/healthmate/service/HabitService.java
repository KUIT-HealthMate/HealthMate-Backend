package com.kuit.healthmate.service;

import com.kuit.healthmate.domain.habit.entity.Habit;
import com.kuit.healthmate.domain.habit.entity.HabitTime;
import com.kuit.healthmate.domain.habit.repository.HabitRepository;
import com.kuit.healthmate.domain.habit.repository.HabitTimeRepository;
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
    private final HabitTimeRepository habitTimeRepository;

    @Transactional
    public Habit createHabit(Habit habit,List<LocalDateTime> times){
        for(LocalDateTime time : times){
            HabitTime habitTime = HabitTime.builder().habit(habit).time(time).build();
            habitTimeRepository.save(habitTime);
        }
        return habitRepository.save(habit);
    }

    @Transactional
    public List<Habit> getActiveHabitsByUserIdAndToday(Long userId) {
        int dayOfWeek = LocalDateTime.now().getDayOfWeek().getValue();
        // 요일을 월요일부터 시작하는 1부터 7까지의 값으로 맞추기 위해 필요
        dayOfWeek = (dayOfWeek % 7) + 1; // 월요일이 1, 화요일이 2, ..., 일요일이 7

        return habitRepository.findActiveHabitsByUserIdAndDayOfWeek(userId, dayOfWeek);
    }

    @Transactional
    public void updateHabit(Habit habit,List<LocalDateTime> times){
        Long habitId = habit.getId();
        //Habit habit = habitRepository.findById(habitId);
        habitRepository.save(habit);

        // 기존 HabitTime 삭제하고
        // 새로운 HabitTime 추가
        habitTimeRepository.deleteAll(habit.getHabitTime());
        for (LocalDateTime time : times) {
            HabitTime habitTime = HabitTime.builder().habit(habit).time(time).build();
            habitTimeRepository.save(habitTime);
        }
    }
    @Transactional
    public void deleteHabit(Long habitId){
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 habit Id"));

    }
}
