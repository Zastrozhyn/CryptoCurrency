package com.idfinance.service.service.impl;

import com.idfinance.repository.dao.CurrentCryptoRepository;
import com.idfinance.repository.entity.CurrentCrypto;
import com.idfinance.service.dto.CurrentCryptoDto;
import com.idfinance.service.exception.EntityException;
import com.idfinance.service.exception.ExceptionCode;
import com.idfinance.service.mapper.CurrentCryptoMapper;
import com.idfinance.service.service.CurrentCryptoService;
import com.idfinance.service.service.UserService;
import com.idfinance.service.util.AvailableCryptoUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Log4j2
@EnableScheduling
public class CurrentCryptoServiceImpl implements CurrentCryptoService {

    private final CurrentCryptoRepository currentCryptoRepository;
    private final RestTemplate restTemplate;
    private final AvailableCryptoUtil availableCryptoUtil;
    private final CurrentCryptoMapper mapper = Mappers.getMapper(CurrentCryptoMapper.class);
    private final Map<String, CurrentCrypto> cryptoCache = new HashMap<>();
    private final UserService userService;

    @Autowired
    public CurrentCryptoServiceImpl(CurrentCryptoRepository currentCryptoRepository,
                                    RestTemplate restTemplate, AvailableCryptoUtil availableCryptoUtil, UserService userService) {
        this.currentCryptoRepository = currentCryptoRepository;
        this.restTemplate = restTemplate;
        this.availableCryptoUtil = availableCryptoUtil;
        this.userService = userService;
    }

    @PostConstruct
    private void init(){
        findAllCrypto().forEach(o -> cryptoCache.put(o.getSymbol(), o));
    }

    @Override
    public CurrentCryptoDto findCryptoByCode(String code) {
        CurrentCrypto currentCrypto = currentCryptoRepository.findBySymbolContainingIgnoreCase(code)
                .orElseThrow(() -> new EntityException(ExceptionCode.CRYPTO_NOT_FOUND.getErrorCode()));
        return mapper.mapToDto(currentCrypto);
    }

    @Override
    public List<CurrentCryptoDto> findAllCrypto(Pageable pageable) {
        return mapper.mapToDto(currentCryptoRepository.findAll(pageable).getContent());
    }

    @Override
    @Scheduled(cron = "${CryptoApi.scheduled.cron.expression}")
    @Transactional
    public void updateAllCrypto() {
        List<String> urls = availableCryptoUtil.getUrlsForUpdateCryptos();
        for (String url:urls){
            CurrentCrypto currentCrypto = mapper.mapToEntity(readCryptoFromExternalApi(url));
            userService.alertCurrencyChanging(currentCrypto);
            if (isPriceChanged(currentCrypto)){
                currentCryptoRepository.save(currentCrypto);
                cryptoCache.remove(currentCrypto.getSymbol());
                cryptoCache.put(currentCrypto.getSymbol(), currentCrypto);
            }
        }
    }

    private CurrentCryptoDto readCryptoFromExternalApi(String url) {
        CurrentCryptoDto[] cryptos = restTemplate
                .getForObject(url, CurrentCryptoDto[].class);
        if (cryptos!= null && cryptos.length > 0){
            return cryptos[0];
        } else {
            log.warn(ExceptionCode.CAN_NOT_READ_CRYPTO + url + LocalDateTime.now());
            return null;
        }
    }

    private List<CurrentCrypto> findAllCrypto() {
        return currentCryptoRepository.findAll();
    }

    private boolean isPriceChanged(CurrentCrypto crypto){
        return !crypto.getPriceUsd().equals(cryptoCache.get(crypto.getSymbol()).getPriceUsd());
    }
}
