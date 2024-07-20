package com.kuit.healthmate.service;

import com.kuit.healthmate.domain.Status;
import com.kuit.healthmate.repository.SupplementCheckerRepository;
import com.kuit.healthmate.repository.SupplementRepository;
import com.kuit.healthmate.repository.SupplementTimeRepository;
import com.kuit.healthmate.dto.supplement.SupplementResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupplementService {

    private final SupplementRepository supplementRepository;
    private final SupplementCheckerRepository supplementCheckerRepository;
    private final SupplementTimeRepository supplementTimeRepository;

    public List<SupplementResponse> getSupplementChallengesByUserId(Long userId) {
        return supplementRepository.findAllByUserIdAndStatus(userId, Status.ACTIVE)
                .stream()
                .map(SupplementResponse::new)
                .collect(Collectors.toList());
    }
}
