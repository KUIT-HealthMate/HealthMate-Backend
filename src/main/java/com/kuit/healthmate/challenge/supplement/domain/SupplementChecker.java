package com.kuit.healthmate.challenge.supplement.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
@Getter
public class SupplementChecker {

    @Id
    @GeneratedValue
    @Column(name = "supplement_checker_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="supplement_id")
    private Supplement supplement;

    private LocalDate checkDate;
    @Enumerated(EnumType.STRING)
    private TimeSlot timeSlot;
    private Boolean status;

    public boolean toggleStatus() {
        this.status = !this.status;
        return this.status;
    }

    public SupplementChecker(Supplement supplement, TimeSlot timeSlot) {
        this.supplement = supplement;
        this.checkDate = LocalDate.now();
        this.timeSlot = timeSlot;
        this.status = Boolean.TRUE;
    }

}
