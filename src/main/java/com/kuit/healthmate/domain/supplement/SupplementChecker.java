package com.kuit.healthmate.domain.supplement;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table
public class SupplementChecker {

    @Id
    @GeneratedValue
    @Column(name = "supplement_checker_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="supplement_id")
    private Supplement supplement;

    private LocalDate checkDate;
    private TimeSlot timeSlot;
    private Boolean status;
}
