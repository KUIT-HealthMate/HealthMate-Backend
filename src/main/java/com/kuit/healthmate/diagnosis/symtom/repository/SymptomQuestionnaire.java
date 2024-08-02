package com.kuit.healthmate.diagnosis.symtom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SymptomQuestionnaire extends JpaRepository<SymptomQuestionnaire,Long> {
}
