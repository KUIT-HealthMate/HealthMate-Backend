package com.kuit.healthmate.diagnosis.symtom.repository;

import com.kuit.healthmate.diagnosis.symtom.domain.SymptomQuestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SymptomQuestionnaireRepository extends JpaRepository<SymptomQuestionnaire,Long> {
}
