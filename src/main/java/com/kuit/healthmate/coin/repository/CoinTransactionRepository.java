package com.kuit.healthmate.coin.repository;

import com.kuit.healthmate.coin.domain.CoinTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CoinTransactionRepository extends JpaRepository<CoinTransaction, Long> {

    @Query("SELECT c FROM CoinTransaction c WHERE c.userId =:userId")
    List<CoinTransaction> findByUserId(@Param("userId") Long userId);
}
