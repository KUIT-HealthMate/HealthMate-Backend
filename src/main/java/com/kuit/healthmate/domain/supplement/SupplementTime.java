package com.kuit.healthmate.domain.supplement;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalTime;

@Entity
@Table
public class SupplementTime {
    @Id
    @GeneratedValue
    @Column(name = "supplement_time_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="supplement_id")
    private Supplement supplement;

    private TimeSlot timeSlot;

    private LocalTime time;
}
