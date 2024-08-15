package com.kuit.healthmate.challenge.supplement.service;

import com.kuit.healthmate.challenge.common.domain.Status;
import com.kuit.healthmate.challenge.supplement.domain.Supplement;
import com.kuit.healthmate.challenge.supplement.domain.SupplementChecker;
import com.kuit.healthmate.challenge.supplement.domain.SupplementRoutine;
import com.kuit.healthmate.challenge.supplement.domain.SupplementTime;
import com.kuit.healthmate.challenge.supplement.dto.CustomTime;
import com.kuit.healthmate.challenge.supplement.dto.SupplementEditListResponse;
import com.kuit.healthmate.challenge.supplement.dto.SupplementEditResponse;
import com.kuit.healthmate.challenge.supplement.dto.constant.WeekOfDays;
import com.kuit.healthmate.user.domain.User;
import com.kuit.healthmate.challenge.supplement.dto.SupplementCheckerRequest;
import com.kuit.healthmate.challenge.supplement.dto.SupplementRegisterRequest;
import com.kuit.healthmate.challenge.supplement.dto.SupplementUpdateRequest;
import com.kuit.healthmate.global.exception.SupplementException;
import com.kuit.healthmate.global.exception.UserException;
import com.kuit.healthmate.global.response.ExceptionResponseStatus;
import com.kuit.healthmate.challenge.supplement.repository.SupplementCheckerRepository;
import com.kuit.healthmate.challenge.supplement.repository.SupplementRepository;
import com.kuit.healthmate.challenge.supplement.repository.SupplementTimeRepository;
import com.kuit.healthmate.challenge.supplement.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupplementService {

    private final UserRepository userRepository;
    private final SupplementRepository supplementRepository;
    private final SupplementCheckerRepository supplementCheckerRepository;
    private final SupplementTimeRepository supplementTimeRepository;

    @Transactional
    public Long registerSupplement(Long userId, SupplementRegisterRequest supplementRegisterRequest) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserException(ExceptionResponseStatus.INVALID_USER_ID)
        );
        String name = supplementRegisterRequest.getName();
        SupplementRoutine supplementRoutine = supplementRegisterRequest.getSupplementRoutine();

        Supplement supplement = new Supplement(user, name,
                supplementRoutine);// TODO: NULL related exception should be held
        supplementRepository.save(supplement);  // 뒤에서 save해도 persistence context will manage the object?

        List<SupplementTime> supplementTimes = supplementRegisterRequest.getNotificationTime()
                .stream()
                .map(time -> new SupplementTime(supplement, time.toLocalTime()))
                .toList();

        supplementTimeRepository.saveAll(supplementTimes);
        supplement.setSupplementTimes(supplementTimes); // 양방향도 설정 해 주기

        return supplement.getId();
    }

    @Transactional
    public void updateSupplement(Long supplementId, SupplementUpdateRequest supplementUpdateRequest) {
        Supplement supplement = supplementRepository.findById(supplementId).orElseThrow(
                () -> new SupplementException(ExceptionResponseStatus.INVALID_SUPPLEMENT_ID)
        );

        supplement.update(supplementUpdateRequest.getName(), supplementUpdateRequest.getAfterMeal(),
                supplementUpdateRequest.getSelectedDay(), supplementUpdateRequest.isBreakfast(),
                supplementUpdateRequest.isLunch(), supplementUpdateRequest.isDinner(), supplementUpdateRequest.getTimes());
    }

    @Transactional
    public void deleteSupplement(Long supplementId) {
        Supplement supplement = supplementRepository.findById(supplementId).orElseThrow(
                () -> new SupplementException(ExceptionResponseStatus.INVALID_SUPPLEMENT_ID)
        );

        supplement.setStatus(Status.INACTIVE);
    }

    @Transactional
    public Boolean checkSupplementChecker(Long supplementId, SupplementCheckerRequest supplementCheckerRequest) {
        Supplement supplement = supplementRepository.findSupplementAndChecker(supplementId)
                .orElseThrow(
                        () -> new SupplementException(ExceptionResponseStatus.INVALID_SUPPLEMENT_ID)
                );

        SupplementChecker supplementChecker = supplement.getSupplementCheckers().stream()
                .filter(x -> x.isDateMatch(LocalDate.now()))
                .filter(x -> x.isTimeSlotMatch(supplementCheckerRequest.getTimeSlot()))
                .findAny()
                .orElseGet(
                        () -> supplementCheckerRepository.save(
                                    new SupplementChecker(supplement, supplementCheckerRequest.getTimeSlot())
                            )
                );

        return supplementChecker.toggleStatus();
    }

    public SupplementEditResponse getSupplementEditResponse(Long supplementId) {
//        Supplement supplement = supplementRepository.findSupplementAndChecker(supplementId) // TODO: notiTime까지 가져오는 애로 다시 짜야해
//                .orElseThrow(
//                        () -> new SupplementException(ExceptionResponseStatus.INVALID_SUPPLEMENT_ID)
//                );
//
//        return new SupplementEditResponse(supplement);
        HashMap<String, Integer> intakeTime = new HashMap<>();
        intakeTime.put("beforeOrAfterMeal", 2);
        intakeTime.put("minutes", 30);

        HashMap<String, Boolean> dailyIntakePeriod = new HashMap<>();
        dailyIntakePeriod.put("breakfast", true);
        dailyIntakePeriod.put("lunch", true);
        dailyIntakePeriod.put("dinner", true);

        HashMap<String, Boolean> weeklyIntakeFrequency = new HashMap<>();
        for(WeekOfDays days : WeekOfDays.values()) {
            weeklyIntakeFrequency.put(days.getKey(), Boolean.TRUE);
        }

        List<CustomTime> notificationTime = List.of(CustomTime.ofLocalTime(LocalTime.of(17, 0)),
                CustomTime.ofLocalTime(LocalTime.of(12, 0)),
                CustomTime.ofLocalTime(LocalTime.of(8, 0))
        );

        return new SupplementEditResponse(
                "목데이터",
                1L,
                intakeTime,
                dailyIntakePeriod,
                weeklyIntakeFrequency,
                notificationTime
        );
    }

    public List<Supplement> getSupplementEditList(Long userId) {
        return supplementRepository.findAllByUserIdAndStatus(userId, Status.ACTIVE);
    }

    public List<Supplement> getSupplementForDay(Long userId, LocalDate localDate) {
        return supplementRepository.findAllByUserIdAndCheckedDateBetween(userId, localDate, localDate);
    }

    public List<Supplement> getSupplementForToday(Long userId) {
        return supplementRepository.findAllByUserIdAndCheckedDateBetween(userId, LocalDate.now(), LocalDate.now());
    }

    public List<Supplement> getSupplementBetween(Long userId, LocalDate startDate, LocalDate endDate) {
        return supplementRepository.findAllByUserIdAndCheckedDateBetween(userId, startDate, endDate);
    }
}
