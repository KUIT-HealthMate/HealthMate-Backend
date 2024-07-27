package com.kuit.healthmate.challenge.supplement.repository;

import com.kuit.healthmate.challenge.common.domain.Status;
import com.kuit.healthmate.challenge.supplement.domain.Supplement;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SupplementRepository extends JpaRepository<Supplement, Long> {

    List<Supplement> findAllByUserIdAndStatus(Long userId, Status status);

    @Query("select distinct s from Supplement s join fetch s.supplementCheckers sc "
            + "where s.user.id = :userId and sc.checkDate between :startDate and :endDate")
    List<Supplement> findAllByUserIdAndCheckedDateBetween(@Param("userId") Long UserId,
                                                          @Param("startDate") LocalDate startDate,
                                                          @Param("endDate") LocalDate endDate);
}
