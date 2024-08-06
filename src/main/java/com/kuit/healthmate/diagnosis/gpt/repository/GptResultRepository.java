package com.kuit.healthmate.diagnosis.gpt.repository;

import com.kuit.healthmate.diagnosis.gpt.domain.GptResultToday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GptResultRepository extends JpaRepository<GptResultToday,Long> {
    @Query("SELECT g from GptResultToday g where g.date between :startDate and :endDate")
    List<GptResultToday> findAllByDateABetween(@Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);
}
