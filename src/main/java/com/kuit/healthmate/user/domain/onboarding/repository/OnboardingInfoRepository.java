package com.kuit.healthmate.user.domain.onboarding.repository;

import com.kuit.healthmate.user.domain.User;
import com.kuit.healthmate.user.domain.onboarding.domain.OnboardingInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OnboardingInfoRepository extends JpaRepository<OnboardingInfo, Long> {
    @Query("select o from OnboardingInfo o join fetch o.symptoms s "
            + "where o.userId = :userId")
    Optional<OnboardingInfo> findByUserId(@Param("userId") Long userId);
}