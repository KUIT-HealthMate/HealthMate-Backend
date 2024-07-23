package com.kuit.healthmate.challenge.supplement.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table
public class SupplementTime {

    @Id
    @GeneratedValue
    @Column(name = "supplement_time_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="supplement_id")
    private Supplement supplement;

    private LocalTime time;

    public SupplementTime(Supplement supplement, LocalTime time) {
        this.supplement = supplement;
        this.time = time;
    }
}
