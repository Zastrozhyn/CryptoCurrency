package com.idfinance.service.service;

import com.idfinance.service.dto.CurrentCryptoDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CurrentCryptoService {
    CurrentCryptoDto findCryptoByCode(String code);

    List<CurrentCryptoDto> findAllCrypto(Pageable pageable);

    void updateAllCrypto();
}
