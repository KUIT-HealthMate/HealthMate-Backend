package com.kuit.healthmate.user.domain.onboarding.domain;

import com.kuit.healthmate.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "onboarding")
@Getter
public class OnboardingInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int gender; // 1: 여성, 2: 남성

    @Column(nullable = false)
    private int ageGroup; // 1: 40대 이하, 2: 50대, 3: 60대, 4: 70대 이상

    @ElementCollection
    @CollectionTable(name = "onboarding_symptoms", joinColumns = @JoinColumn(name = "onboarding_info_id"))
    @Column(name = "symptom")
    private List<String> symptoms; // 현재 겪고 있는 증상 리스트


    @ElementCollection
    @CollectionTable(name = "onboarding_purpose", joinColumns = @JoinColumn(name = "onboarding_info_id"))
    @Column(name = "purpose")
    private List<Integer> purpose; // 1: 루틴, 2: 질환 예방, 3: 정보 공유, 4: 건강 상태 파악, 5: 약 복용 관리, 6: 생활습관 관리

    private Long userId;

    @Builder
    public OnboardingInfo(Long user, int gender, int ageGroup, List<String> symptoms, List<Integer> purpose) {
        this.userId = user;
        this.gender = gender;
        this.ageGroup = ageGroup;
        this.symptoms = symptoms;
        this.purpose = purpose;
    }
}
