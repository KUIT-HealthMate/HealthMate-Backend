package com.kuit.healthmate.repository;

import com.kuit.healthmate.domain.supplement.SupplementChecker;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplementCheckerRepository extends JpaRepository<SupplementChecker, Long> {

    SupplementChecker findBySupplementIdAndCheckDate(Long supplementId, LocalDate localDate);
}
