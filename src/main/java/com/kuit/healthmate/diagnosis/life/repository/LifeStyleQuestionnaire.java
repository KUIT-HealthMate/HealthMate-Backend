package com.kuit.healthmate.diagnosis.life.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LifeStyleQuestionnaire extends JpaRepository<LifeStyleQuestionnaire, Long> {
}
