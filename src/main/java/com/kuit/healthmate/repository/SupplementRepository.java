package com.kuit.healthmate.repository;

import com.kuit.healthmate.domain.Status;
import com.kuit.healthmate.domain.supplement.Supplement;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplementRepository extends JpaRepository<Supplement, Long> {

    List<Supplement> findAllByUserIdAndStatus(Long userId, Status status);
}
