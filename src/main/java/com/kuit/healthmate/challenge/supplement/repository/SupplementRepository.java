package com.kuit.healthmate.challenge.supplement.repository;

import com.kuit.healthmate.challenge.common.domain.Status;
import com.kuit.healthmate.challenge.supplement.domain.Supplement;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplementRepository extends JpaRepository<Supplement, Long> {

    List<Supplement> findAllByUserIdAndStatus(Long userId, Status status);
}
