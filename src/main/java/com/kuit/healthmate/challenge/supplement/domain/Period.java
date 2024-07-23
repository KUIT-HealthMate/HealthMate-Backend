package com.kuit.healthmate.challenge.supplement.domain;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class Period {
    private LocalDate startDate;
    private LocalDate endDate;

    protected Period() {

    }

    public Period(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
