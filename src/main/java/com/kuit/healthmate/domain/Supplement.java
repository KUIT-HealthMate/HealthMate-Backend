package com.kuit.healthmate.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "supplements")
public class Supplement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplement_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    private String name;
    private String memo;

    @Embedded
    private Period period;

    private Boolean breakfast;
    private Boolean lunch;
    private Boolean dinner;

    @Enumerated(EnumType.STRING)
    private SupplementStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
