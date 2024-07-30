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
    private SymptomInfo first;

    @Embedded
    private SymptomInfo second;

    @Embedded
    private SymptomInfo third;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
