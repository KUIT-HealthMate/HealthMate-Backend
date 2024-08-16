package com.kuit.healthmate.diagnosis.gpt.repository;

import com.kuit.healthmate.diagnosis.gpt.domain.GptResult;
import com.kuit.healthmate.diagnosis.gpt.domain.GptWeekResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GptWeekResultRepository extends JpaRepository<GptWeekResult,Long> {
    @Query("SELECT gr FROM GptWeekResult gr WHERE gr.userId = :userId AND gr.week = :week AND gr.year = :year")
    Optional<GptWeekResult> findDiagnosisResultByUserIdAndDate(@Param("userId") Long userId, @Param("week") Long week, @Param("year") Long year);

    @Query("SELECT gr FROM GptWeekResult gr WHERE gr.userId = :userId AND gr.month = :month AND gr.year = :year")
    List<GptWeekResult> findAllByUserIdAndYearAndMonth(@Param("userId") Long userId, @Param("month") Long month, @Param("year") Long year);
}
