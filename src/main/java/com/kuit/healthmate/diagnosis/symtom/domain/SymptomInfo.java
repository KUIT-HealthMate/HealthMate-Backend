package com.kuit.healthmate.diagnosis.symtom.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class SymptomInfo {
    private String symptomName;
}
