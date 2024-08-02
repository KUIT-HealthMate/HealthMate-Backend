package com.kuit.healthmate.diagnosis.sleep.repository;

import com.kuit.healthmate.diagnosis.sleep.domain.SleepPatternQuestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SleepPatternQuestionnaireRepository extends JpaRepository<SleepPatternQuestionnaire,Long> {
}
