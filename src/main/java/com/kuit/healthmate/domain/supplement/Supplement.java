package com.kuit.healthmate.domain.supplement;

import com.kuit.healthmate.domain.Period;
import com.kuit.healthmate.domain.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "supplement", cascade = CascadeType.ALL)
    private List<SupplementChecker> supplementCheckers = new ArrayList<>();

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
