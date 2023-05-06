package com.idfinance.web.controller;

import com.idfinance.service.service.CurrentCryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cryptos")
public class CurrentCryptoController {

    private final CurrentCryptoService cryptoService;

    @Autowired
    public CurrentCryptoController(CurrentCryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping()
    public void findUserById() {
        cryptoService.updateAllCrypto();
    }
}
