package com.kuit.healthmate.diagnosis.symtom.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "symptom")
@Getter
public class SymptomQuestionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "symptomType", column = @Column(name = "first_symptomType")),
            @AttributeOverride(name = "symptomName", column = @Column(name = "first_symptomName"))
    })
    private SymptomInfo first;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "symptomType", column = @Column(name = "second_symptomType")),
            @AttributeOverride(name = "symptomName", column = @Column(name = "second_symptomName"))
    })
    private SymptomInfo second;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "symptomType", column = @Column(name = "third_symptomType")),
            @AttributeOverride(name = "symptomName", column = @Column(name = "third_symptomName"))
    })
    private SymptomInfo third;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
