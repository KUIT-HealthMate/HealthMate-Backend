package com.kuit.healthmate.user.domain.coin.service;


import com.kuit.healthmate.challenge.supplement.repository.UserRepository;
import com.kuit.healthmate.user.domain.coin.domain.CoinTransaction;
import com.kuit.healthmate.user.domain.coin.repository.CoinTransactionRepository;
import com.kuit.healthmate.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoinService {
    private static final Long COIN_AMOUNT = 10L; // 일단 10 으로 설정
    private CoinTransactionRepository coinTransactionRepository;

    private UserRepository userRepository;

    @Transactional
    public void earnCoins(Long userId, String activity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.addCoins(COIN_AMOUNT);
        userRepository.save(user);

        CoinTransaction transaction = new CoinTransaction(userId, CoinTransaction.TransactionType.EARN, COIN_AMOUNT, activity);
        coinTransactionRepository.save(transaction);
    }
    @Transactional
    public void useCoins(Long userId, String activity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.subtractCoins(COIN_AMOUNT);
        userRepository.save(user);

        CoinTransaction transaction = new CoinTransaction(userId, CoinTransaction.TransactionType.USE, COIN_AMOUNT, activity);
        coinTransactionRepository.save(transaction);
    }

    public List<CoinTransaction> getUserCoinTransactions(Long userId) {
        return coinTransactionRepository.findByUserId(userId);
    }
}
