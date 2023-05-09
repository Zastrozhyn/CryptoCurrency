package com.idfinance.repository.dao;

import com.idfinance.repository.entity.RegisteredCrypto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisteredCryptoRepository extends JpaRepository<RegisteredCrypto, Long> {
    void deleteBySymbolAndUserId(String code, Long userId);
}
