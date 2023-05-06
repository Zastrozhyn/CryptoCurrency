package com.idfinance.service.service;

import com.idfinance.repository.entity.CurrentCrypto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CurrentCryptoService {
    CurrentCrypto findCryptoByCode(String code);
    List<CurrentCrypto> findAllCrypto(Pageable pageable);
    void updateAllCrypto();
}
