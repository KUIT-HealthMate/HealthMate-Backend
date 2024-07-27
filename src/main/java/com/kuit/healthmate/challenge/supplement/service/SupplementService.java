package com.kuit.healthmate.challenge.supplement.service;

import com.kuit.healthmate.challenge.common.domain.Status;
import com.kuit.healthmate.challenge.supplement.domain.Supplement;
import com.kuit.healthmate.challenge.supplement.domain.SupplementChecker;
import com.kuit.healthmate.challenge.supplement.domain.SupplementRoutine;
import com.kuit.healthmate.challenge.supplement.domain.SupplementTime;
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
import com.kuit.healthmate.challenge.supplement.dto.SupplementResponse;
import com.kuit.healthmate.challenge.supplement.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
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

    public List<SupplementResponse> getSupplementChallengesByUserId(Long userId) {
        return supplementRepository.findAllByUserIdAndStatus(userId, Status.ACTIVE)
                .stream()
                .map(SupplementResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long registerSupplement(SupplementRegisterRequest supplementRegisterRequest) {
        User user = userRepository.findById(supplementRegisterRequest.getUserId()).orElseThrow(
                () -> new UserException(ExceptionResponseStatus.INVALID_USER_ID)
        );
        String name = supplementRegisterRequest.getName();
        SupplementRoutine supplementRoutine = supplementRegisterRequest.getSupplementRoutine();

        Supplement supplement = new Supplement(user, name,
                supplementRoutine);// TODO: NULL related exception should be held
        supplementRepository.save(supplement);  // 뒤에서 save해도 persistence context will manage the object?

        List<SupplementTime> supplementTimes = supplementRegisterRequest.getTimes()
                .stream()
                .map(time -> new SupplementTime(supplement, time))
                .toList();

        supplementTimeRepository.saveAll(supplementTimes);
        supplement.setSupplementTimes(supplementTimes); // 양방향도 설정 해 주기

        return supplement.getId();
    }

    @Transactional
    public void updateSupplement(Long supplementId, SupplementUpdateRequest supplementUpdateRequest) {
        Supplement supplement = supplementRepository.findById(supplementId).orElseThrow(
                () -> new UserException(ExceptionResponseStatus.INVALID_USER_ID)
        );

        supplement.update(supplementUpdateRequest.getName(), supplementUpdateRequest.getAfterMeal(),
                supplementUpdateRequest.getSelectedDay(), supplementUpdateRequest.isBreakfast(),
                supplementUpdateRequest.isLunch(), supplementUpdateRequest.isDinner(), supplementUpdateRequest.getTimes());
    }

    @Transactional
    public void deleteSupplement(Long supplementId) {
        Supplement supplement = supplementRepository.findById(supplementId).orElseThrow(
                () -> new UserException(ExceptionResponseStatus.INVALID_USER_ID)
        );

        supplement.setStatus(Status.INACTIVE);
    }

    @Transactional
    public Boolean checkSupplementChecker(Long supplementId, SupplementCheckerRequest supplementCheckerRequest) {
        Supplement supplement = supplementRepository.findById(supplementId).orElseThrow(
                () -> new SupplementException(ExceptionResponseStatus.INVALID_SUPPLEMENT_ID)
        );
        SupplementChecker supplementChecker = supplementCheckerRepository.findBySupplementIdAndCheckDateAndTimeSlot(
                        supplementId, LocalDate.now(), supplementCheckerRequest.getTimeSlot())
                .orElse(
                        supplementCheckerRepository.save(
                                new SupplementChecker(supplement, supplementCheckerRequest.getTimeSlot())
                        )
                );
        return supplementChecker.getStatus();
    }

    public List<Supplement> getSupplementForDay(Long userId, LocalDate localDate) {
        return supplementRepository.findAllByUserIdAndCheckedDateBetween(userId, localDate, localDate);
    }

    public List<Supplement> getSupplementForToday(Long userId) {
        return supplementRepository.findAllByUserIdAndCheckedDateBetween(userId, LocalDate.now(), LocalDate.now());
    }

    public List<Supplement> getSupplementForMonth(Long userId, LocalDate endDate) {
        return supplementRepository.findAllByUserIdAndCheckedDateBetween(userId, endDate.withDayOfMonth(1), endDate);
    }

    public List<Supplement> getSupplementForWeek(Long userId, LocalDate startDate, LocalDate endDate) {
        return supplementRepository.findAllByUserIdAndCheckedDateBetween(userId, startDate, endDate);
    }
}
