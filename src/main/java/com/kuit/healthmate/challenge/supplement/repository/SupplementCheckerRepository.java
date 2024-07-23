package com.kuit.healthmate.challenge.supplement.repository;

import com.kuit.healthmate.challenge.supplement.domain.SupplementChecker;
import com.kuit.healthmate.challenge.supplement.domain.TimeSlot;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplementCheckerRepository extends JpaRepository<SupplementChecker, Long> {

    SupplementChecker findBySupplementIdAndCheckDateAndTimeSlot(Long supplementId, LocalDate localDate,
                                                                 TimeSlot timeSlot);
}
