package com.kuit.healthmate.diagnosis.life.repository;

import com.kuit.healthmate.diagnosis.life.domain.LifeStyleQuestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LifeStyleQuestionnaireRepository extends JpaRepository<LifeStyleQuestionnaire, Long> {
    // 특정 주에 해당하는 데이터를 조회하기 위한 메서드
    @Query("SELECT l FROM LifeStyleQuestionnaire l WHERE l.timestamp >= :weekStartDate AND l.timestamp <= :weekEndDate")
    List<LifeStyleQuestionnaire> findByWeek(LocalDateTime weekStartDate, LocalDateTime weekEndDate);
}
