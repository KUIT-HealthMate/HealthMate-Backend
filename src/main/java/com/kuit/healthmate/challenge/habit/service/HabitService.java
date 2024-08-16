package com.kuit.healthmate.challenge.habit.service;

import com.kuit.healthmate.challenge.common.domain.Status;
import com.kuit.healthmate.challenge.habit.domain.Habit;
import com.kuit.healthmate.challenge.habit.domain.HabitChecker;
import com.kuit.healthmate.challenge.habit.domain.HabitTime;
import com.kuit.healthmate.challenge.habit.dto.response.PostCreateHabitResponse;
import com.kuit.healthmate.challenge.habit.repository.HabitCheckerRepository;
import com.kuit.healthmate.challenge.habit.repository.HabitRepository;
import com.kuit.healthmate.challenge.habit.repository.HabitTimeRepository;
import com.kuit.healthmate.challenge.habit.dto.request.PatchEditHabitRequest;
import com.kuit.healthmate.challenge.habit.dto.request.PostCreateHabitRequest;
import com.kuit.healthmate.challenge.habit.dto.SelectedTime;
import com.kuit.healthmate.challenge.supplement.dto.CustomTime;

import com.kuit.healthmate.global.exception.HabitException;
import com.kuit.healthmate.global.response.ExceptionResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HabitService {

    private final HabitRepository habitRepository;
    private final HabitTimeRepository habitTimeRepository;
    private final HabitCheckerRepository habitCheckerRepository;

    @Transactional
    public PostCreateHabitResponse createHabit(PostCreateHabitRequest postCreateHabitRequest, Long userId){
        //userID 주입
        List<CustomTime> times = postCreateHabitRequest.getNotificationTime();
        Habit habit = Habit.builder()
                .name(postCreateHabitRequest.getName())
                .status(String.valueOf(Status.ACTIVE))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .selectedDay(postCreateHabitRequest.getSelectedDay())
                .userId(userId)
                .build();
        log.info(habit.toString());
        habitRepository.save(habit);
        List<HabitTime> habitTimes = postCreateHabitRequest.getNotificationTime()
                .stream()
                .map(time -> new HabitTime(habit, time.toLocalTime()))
                .toList();
        habitTimeRepository.saveAll(habitTimes);

        habit.setHabitTimes(habitTimes);

        return new PostCreateHabitResponse(habit,postCreateHabitRequest.getNotificationTime());
    }

    //특정 날짜 기준 조회 ,,당일 or 특정 날짜
    @Transactional(readOnly = true)
    public List<Habit> getActiveHabitsByUserIdAndToday(Long userId, LocalDate date) {
        int dayOfWeek = date.getDayOfWeek().getValue();
        // 요일을 월요일부터 시작하는 1부터 7까지의 값으로 맞추기 위해 필요
        // 월요일이 1, 화요일이 2, ..., 일요일이 7
        List<Habit> habits = habitRepository.findActiveHabitsByUserIdAndDayOfWeek(userId, dayOfWeek, date);

        List<Habit> processedHabits = new ArrayList<>();

        for (Habit habit : habits) {
            if (habit.getHabitChecker().isEmpty()) {
                // HabitChecker가 없는 경우 INACTIVE 상태로 업데이트된 날짜를 확인하여 추가 처리하는 부분
                if (habit.getStatus() == Status.INACTIVE && habit.getUpdatedAt().toLocalDate().isAfter(date)) {
                    processedHabits.add(habit);
                }
                else if(habit.getStatus() == Status.ACTIVE){
                    processedHabits.add(habit);
                }
            } else {
                processedHabits.add(habit);
            }
        }
        return processedHabits;
    }
    public List<Habit> getHabitForWeek(Long userId, LocalDate startDate,LocalDate endDate){
        List<Habit> habits = habitRepository.findAllByUserIdAndCreatedAtBetween(userId,startDate,endDate);
        log.info(habits.get(0).getName());

        return habits;
    }
    public List<Habit> getHabitForMonth(Long userId, LocalDate endDate){
        List<Habit> habits = habitRepository.findAllByUserIdAndCreatedAtBetween(userId, endDate.withDayOfMonth(1),endDate);

        log.info(habits.get(0).getName());

        return habits;
    }

    @Transactional
    public void updateHabit(Long habitId,PatchEditHabitRequest patchEditHabitRequest){
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new HabitException(ExceptionResponseStatus.NOT_EXIST_HABIT));
        habitRepository.updateHabit(habitId, patchEditHabitRequest.getName(),  LocalDateTime.now(), patchEditHabitRequest.getSelectedDay());
        List<SelectedTime> times = patchEditHabitRequest.getNotificationTime();
        // 기존 HabitTime 삭제하고
        // 새로운 HabitTime 추가
        habitTimeRepository.deleteAll(habit.getHabitTime());
        for (SelectedTime time : times) {
            HabitTime habitTime = new HabitTime(habit, time.toLocalTime());
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
    public void checkHabit(LocalDate date, Long habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new HabitException(ExceptionResponseStatus.NOT_EXIST_HABIT));

        HabitChecker habitChecker = habitCheckerRepository.findByHabitAndCreatedAt(habit, date)
                .map( it ->{
                    it.toggleStatus();
                    return  it;
                        }
                )
                .orElseGet(() -> HabitChecker.builder()
                        .createdAt(date)
                        .status(Boolean.TRUE)
                        .habit(habit).build());

        habitCheckerRepository.save(habitChecker);
    }

    public List<Habit> getSupplementEditList(Long userId) {
        return habitRepository.findAllWithTimesByActiveAndUserId(userId);
    }
}
