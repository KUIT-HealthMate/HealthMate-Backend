package com.kuit.healthmate.challenge.habit.service;

import com.kuit.healthmate.challenge.common.domain.Status;
import com.kuit.healthmate.challenge.habit.domain.Habit;
import com.kuit.healthmate.challenge.habit.domain.HabitChecker;
import com.kuit.healthmate.challenge.habit.domain.HabitTime;
import com.kuit.healthmate.challenge.habit.dto.GetHabitResponse;
import com.kuit.healthmate.challenge.habit.repository.HabitCheckerRepository;
import com.kuit.healthmate.challenge.habit.repository.HabitRepository;
import com.kuit.healthmate.challenge.habit.repository.HabitTimeRepository;
import com.kuit.healthmate.challenge.habit.dto.PatchEditHabitRequest;
import com.kuit.healthmate.challenge.habit.dto.PostCreateHabitRequest;
import com.kuit.healthmate.challenge.habit.dto.SelectedTime;
import com.kuit.healthmate.global.exception.HabitException;
import com.kuit.healthmate.global.response.ExceptionResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HabitService {

    private final HabitRepository habitRepository;
    private final HabitTimeRepository habitTimeRepository;
    private final HabitCheckerRepository habitCheckerRepository;

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
    public List<GetHabitResponse> getActiveHabitsByUserIdAndToday(Long userId, LocalDate date) {
        int dayOfWeek = date.getDayOfWeek().getValue();
        // 요일을 월요일부터 시작하는 1부터 7까지의 값으로 맞추기 위해 필요
        // 월요일이 1, 화요일이 2, ..., 일요일이 7
        List<GetHabitResponse> habits = habitRepository.findActiveHabitsByUserIdAndDayOfWeek(userId, dayOfWeek)
                .stream()
                .map(GetHabitResponse::new)
                .collect(Collectors.toList());
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
    @Transactional
    public void checkHabit(LocalDateTime date, Long habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new HabitException(ExceptionResponseStatus.NOT_EXIST_HABIT));

        HabitChecker habitChecker = habitCheckerRepository.findByHabitAndCreatedAt(habit, LocalDateTime.now())
                .map( it ->{
                    it.toggleStatus();
                    return  it;
                        }
                )
                .orElseGet(() -> HabitChecker.builder()
                        .id(habitId)
                        .createdAt(date)
                        .status(Boolean.TRUE)
                        .habit(habit).build());

        habitCheckerRepository.save(habitChecker);
    }
}
