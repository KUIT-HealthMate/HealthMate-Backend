package com.kuit.healthmate.diagnosis.gpt.repository;

import com.kuit.healthmate.diagnosis.gpt.domain.GptMonthResult;
import com.kuit.healthmate.diagnosis.gpt.domain.GptResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface GptMonthResultRepository extends JpaRepository<GptMonthResult,Long> {
    @Query("SELECT gr FROM GptMonthResult gr WHERE gr.userId = :userId AND gr.month = :month")
    Optional<GptResult> findDiagnosisResultByUserIdAndDate(@Param("userId") Long userId, @Param("month") Long month);
}
