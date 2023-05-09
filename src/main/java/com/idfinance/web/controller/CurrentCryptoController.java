package com.idfinance.web.controller;

import com.idfinance.service.dto.CurrentCryptoDto;
import com.idfinance.service.service.CurrentCryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cryptos")
public class CurrentCryptoController {

    private final CurrentCryptoService cryptoService;


    @Autowired
    public CurrentCryptoController(CurrentCryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping()
    public List<CurrentCryptoDto> findAllCrypto(Pageable pageable) {
        return cryptoService.findAllCrypto(pageable);
    }

    @GetMapping("/search")
    public CurrentCryptoDto findCryptoByCode(@RequestParam(name = "code") String code) {
        return cryptoService.findCryptoByCode(code);
    }
}
