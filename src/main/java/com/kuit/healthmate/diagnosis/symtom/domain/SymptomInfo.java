package com.kuit.healthmate.diagnosis.symtom.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class SymptomInfo {

    private int symptomType; //관련 과에 따라 구분 ex) 피부과, 신경과, 정신과, 기타

    private String symptomName;
}
