package com.idfinance.service.service.impl;

import com.idfinance.repository.dao.CurrentCryptoRepository;
import com.idfinance.repository.entity.CurrentCrypto;
import com.idfinance.service.dto.CurrentCryptoDto;
import com.idfinance.service.exception.EntityException;
import com.idfinance.service.exception.ExceptionCode;
import com.idfinance.service.mapper.CurrentCryptoMapper;
import com.idfinance.service.service.CurrentCryptoService;
import com.idfinance.service.util.AvailableCryptoUtil;
import lombok.extern.log4j.Log4j2;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j2
public class CurrentCryptoServiceImpl implements CurrentCryptoService {

    private final CurrentCryptoRepository currentCryptoRepository;
    private final RestTemplate restTemplate;
    private final AvailableCryptoUtil availableCryptoUtil;
    private final CurrentCryptoMapper mapper = Mappers.getMapper(CurrentCryptoMapper.class);

    @Autowired
    public CurrentCryptoServiceImpl(CurrentCryptoRepository currentCryptoRepository,
                                    RestTemplate restTemplate, AvailableCryptoUtil availableCryptoUtil) {
        this.currentCryptoRepository = currentCryptoRepository;
        this.restTemplate = restTemplate;
        this.availableCryptoUtil = availableCryptoUtil;
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
        List<String> urls = availableCryptoUtil.getUrlsForUpdateCryptos();
        for (String url:urls){
            currentCryptoRepository.save(mapper.mapToEntity(readCryptoFromExternalApi(url)));
            System.out.println(readCryptoFromExternalApi(url));
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
}
