package com.kuit.healthmate.diagnosis.gpt.repository;

import com.kuit.healthmate.diagnosis.gpt.domain.GptResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GptResultRepository extends JpaRepository<GptResult,Long> {
}
