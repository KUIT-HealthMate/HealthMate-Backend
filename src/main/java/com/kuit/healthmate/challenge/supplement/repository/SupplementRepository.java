package com.kuit.healthmate.challenge.supplement.repository;

import com.kuit.healthmate.challenge.common.domain.Status;
import com.kuit.healthmate.challenge.supplement.domain.Supplement;
import com.kuit.healthmate.challenge.supplement.domain.TimeSlot;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SupplementRepository extends JpaRepository<Supplement, Long> {

    List<Supplement> findAllByUserIdAndStatus(Long userId, Status status);

    @Query("select distinct s from Supplement s join fetch s.supplementCheckers sc "
            + "where s.user.id = :userId and sc.checkDate between :startDate and :endDate")
    List<Supplement> findAllByUserIdAndCheckedDateBetween(@Param("userId") Long userId,
                                                          @Param("startDate") LocalDate startDate,
                                                          @Param("endDate") LocalDate endDate);

    @Query("select distinct s from Supplement s join fetch s.supplementCheckers sc "
            + "where s.id = :supplementId")
    Optional<Supplement> findSupplementAndChecker(@Param("supplementId") Long supplementId);
}
