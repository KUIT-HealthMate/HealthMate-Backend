package com.kuit.healthmate.repository;

import com.kuit.healthmate.domain.supplement.Supplement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplementRepository extends JpaRepository<Supplement, Long> {

}
