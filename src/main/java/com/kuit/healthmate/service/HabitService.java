package com.kuit.healthmate.service;

import com.kuit.healthmate.domain.Status;
import com.kuit.healthmate.domain.habit.entity.Habit;
import com.kuit.healthmate.domain.habit.entity.HabitTime;
import com.kuit.healthmate.domain.habit.repository.HabitRepository;
import com.kuit.healthmate.domain.habit.repository.HabitTimeRepository;
import com.kuit.healthmate.dto.habit.PatchEditHabitRequest;
import com.kuit.healthmate.dto.habit.PostCreateHabitRequest;
import com.kuit.healthmate.dto.habit.SelectedTime;
import com.kuit.healthmate.global.exception.HabitException;
import com.kuit.healthmate.global.response.ExceptionResponseStatus;
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
    public Habit createHabit(PostCreateHabitRequest postCreateHabitRequest){
        //user 객체 생성하여 습관에 포함시켜 저장하는 방식으로 수정해야함
        List<SelectedTime> times = postCreateHabitRequest.getTimes();
        Habit habit = Habit.builder()
                .name(postCreateHabitRequest.getName())
                .memo(postCreateHabitRequest.getMemo())
                .status(String.valueOf(Status.ACTIVE))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .selectedDay(postCreateHabitRequest.getSelectedDay())
                .build();
        log.info(habit.toString());
        for(SelectedTime time : times){
            HabitTime habitTime = HabitTime.builder().habit(habit).time(time.getTime()).build();
            habitTimeRepository.save(habitTime);
        }
        return habitRepository.save(habit);
    }

    @Transactional(readOnly = true)
    public List<Habit> getActiveHabitsByUserIdAndToday(Long userId) {
        int dayOfWeek = LocalDateTime.now().getDayOfWeek().getValue();
        // 요일을 월요일부터 시작하는 1부터 7까지의 값으로 맞추기 위해 필요
        // 월요일이 1, 화요일이 2, ..., 일요일이 7
        log.info("dayOfweek : "+dayOfWeek);
        List<Habit> habits = habitRepository.findActiveHabitsByUserIdAndDayOfWeek(userId, dayOfWeek);
        log.info("habits : "+habits.size());
        return habits;
    }

    @Transactional
    public void updateHabit(PatchEditHabitRequest patchEditHabitRequest){
        Long habitId = patchEditHabitRequest.getHabitId();
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new HabitException(ExceptionResponseStatus.NOT_EXIST_HABIT));
        habitRepository.updateHabit(habitId, patchEditHabitRequest.getName(), patchEditHabitRequest.getMemo(), LocalDateTime.now(), patchEditHabitRequest.getSelectedDay());
        List<SelectedTime> times = patchEditHabitRequest.getTimes();
        // 기존 HabitTime 삭제하고
        // 새로운 HabitTime 추가
        habitTimeRepository.deleteAll(habit.getHabitTime());
        for (SelectedTime time : times) {
            HabitTime habitTime = HabitTime.builder().habit(habit).time(time.getTime()).build();
            habitTimeRepository.save(habitTime);
        }
    }
    @Transactional
    public void deleteHabit(Long habitId){
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new HabitException(ExceptionResponseStatus.NOT_EXIST_HABIT));
        habitRepository.updateHabitStatus(habitId);
    }
}