package com.kuit.healthmate.service;

import com.kuit.healthmate.domain.Status;
import com.kuit.healthmate.domain.supplement.Supplement;
import com.kuit.healthmate.domain.supplement.SupplementRoutine;
import com.kuit.healthmate.domain.supplement.SupplementTime;
import com.kuit.healthmate.domain.user.User;
import com.kuit.healthmate.dto.supplement.SupplementRegisterRequest;
import com.kuit.healthmate.global.exception.UserException;
import com.kuit.healthmate.global.response.ExceptionResponseStatus;
import com.kuit.healthmate.repository.SupplementCheckerRepository;
import com.kuit.healthmate.repository.SupplementRepository;
import com.kuit.healthmate.repository.SupplementTimeRepository;
import com.kuit.healthmate.dto.supplement.SupplementResponse;
import com.kuit.healthmate.repository.UserRepository;
import jakarta.transaction.Transactional;
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
    public void registerSupplement(SupplementRegisterRequest supplementRegisterRequest) {
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
    }
}
