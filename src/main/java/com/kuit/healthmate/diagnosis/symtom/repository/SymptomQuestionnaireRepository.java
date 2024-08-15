package com.kuit.healthmate.diagnosis.symtom.repository;

import com.kuit.healthmate.diagnosis.symtom.domain.SymptomQuestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SymptomQuestionnaireRepository extends JpaRepository<SymptomQuestionnaire,Long> {
    @Query("SELECT s FROM SymptomQuestionnaire s WHERE s.timestamp >= :StartDate AND s.timestamp <= :EndDate")
    List<SymptomQuestionnaire> findByDate(LocalDateTime StartDate, LocalDateTime EndDate);
}
