package com.kuit.healthmate.diagnosis.life.repository;

import com.kuit.healthmate.diagnosis.life.domain.LifeStyleQuestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LifeStyleQuestionnaireRepository extends JpaRepository<LifeStyleQuestionnaire, Long> {
}
