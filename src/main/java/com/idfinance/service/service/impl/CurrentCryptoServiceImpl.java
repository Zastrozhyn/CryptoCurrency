package com.idfinance.service.service.impl;

import com.idfinance.repository.dao.CurrentCryptoRepository;
import com.idfinance.repository.entity.CurrentCrypto;
import com.idfinance.service.dto.CurrentCryptoDto;
import com.idfinance.service.exception.EntityException;
import com.idfinance.service.exception.ExceptionCode;
import com.idfinance.service.service.CurrentCryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CurrentCryptoServiceImpl implements CurrentCryptoService {

    private final CurrentCryptoRepository currentCryptoRepository;

    @Autowired
    public CurrentCryptoServiceImpl(CurrentCryptoRepository currentCryptoRepository) {
        this.currentCryptoRepository = currentCryptoRepository;
    }

    @Override
    public CurrentCrypto findCryptoByCode(String code) {
        return currentCryptoRepository.findBySymbol(code)
                .orElseThrow(() -> new EntityException(ExceptionCode.CRYPTO_NOT_FOUND.getErrorCode()));
    }

    @Override
    public List<CurrentCrypto> findAllCrypto(Pageable pageable) {
        return currentCryptoRepository.findAll(pageable).getContent();
    }

    @Override
    public void updateAllCrypto() {
        RestTemplate restTemplate = new RestTemplate();
        CurrentCryptoDto[] currentCryptoDto = restTemplate
                .getForObject("https://api.coinlore.net/api/ticker/?id=90", CurrentCryptoDto[].class);
        System.out.println(Arrays.toString(currentCryptoDto));
    }
}
