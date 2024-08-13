package com.kuit.healthmate.user.domain.coin.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coins")
@Getter
public class CoinTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "habit_id")
    private Long id;

    private Long userId; // uid로 조회

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private Long coinAmount; // 적립 또는 사용된 코인

    private String activity; // 활동 이름

    private LocalDateTime timestamp;

    @Builder
    public CoinTransaction(Long userId, TransactionType transactionType, Long coinAmount, String activity) {
        this.userId = userId;
        this.transactionType = transactionType;
        this.coinAmount = coinAmount;
        this.activity = activity;
        this.timestamp = LocalDateTime.now();
    }


    public enum TransactionType {
        EARN, USE
    }
}