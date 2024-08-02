package com.kuit.healthmate.diagnosis.sleep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SleepPatternQuestionnaire extends JpaRepository<SleepPatternQuestionnaire,Long> {
}
