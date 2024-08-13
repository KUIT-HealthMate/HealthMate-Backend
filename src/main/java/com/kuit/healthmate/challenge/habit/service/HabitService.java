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
import com.kuit.healthmate.challenge.supplement.domain.SupplementTime;
import com.kuit.healthmate.challenge.supplement.dto.CustomTime;
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
        return habitRepository.findActiveHabitsByUserIdAndDayOfWeek(userId, dayOfWeek);
    }
    public List<Habit> getHabitForWeek(Long userId, LocalDate startDate,LocalDate endDate){
        return habitRepository.findAllByUserIdAndCreatedAtBetween(userId,startDate,endDate);
    }
    public List<Habit> getHabitForMonth(Long userId, LocalDate endDate){
        return habitRepository.findAllByUserIdAndCreatedAtBetween(userId, endDate.withDayOfMonth(1),endDate);
    }

    @Transactional
    public void updateHabit(Long habitId,PatchEditHabitRequest patchEditHabitRequest){
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new HabitException(ExceptionResponseStatus.NOT_EXIST_HABIT));
        habitRepository.updateHabit(habitId, patchEditHabitRequest.getName(),  LocalDateTime.now(), patchEditHabitRequest.getSelectedDay());
        List<SelectedTime> times = patchEditHabitRequest.getTimes();
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

        HabitChecker habitChecker = habitCheckerRepository.findByHabitAndCreatedAt(habit, LocalDate.now())
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
}
