package com.idfinance.repository.dao;

import com.idfinance.repository.entity.CurrentCrypto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrentCryptoRepository extends JpaRepository<CurrentCrypto, Long> {
    Optional<CurrentCrypto> findBySymbol(String symbol);
}
