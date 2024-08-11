package com.kuit.healthmate.diagnosis.gpt.repository;

import com.kuit.healthmate.diagnosis.gpt.domain.GptResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GptResultRepository extends JpaRepository<GptResult,Long> {
    @Query("SELECT gr FROM GptResult gr WHERE gr.userId = :userId AND gr.date = :date")
    GptResult findDiagnosisResultByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);
}
