package com.idfinance.service.integrationtest;

import com.idfinance.service.exception.EntityException;
import com.idfinance.service.exception.ExceptionCode;
import com.idfinance.service.service.CurrentCryptoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CurrentCryptoServiceTest extends BaseIntegrationTest{

    private static final Integer AMOUNT_OF_CRYPTO_IN_DB = 1;
    private static final String CRYPTO_CODE = "BTC";
    private static final String NOT_EXISTING_CRYPTO_CODE = "Q";
    private static final int PAGE = 0;
    private static final int PAGE_SIZE = 10;

    private final CurrentCryptoService service;

    @Autowired
    public CurrentCryptoServiceTest(CurrentCryptoService service) {
        this.service = service;
    }

    @Test
    void findAllShouldReturn3() {
        assertThat(service.findAllCrypto(PageRequest.of(PAGE, PAGE_SIZE))).hasSize(AMOUNT_OF_CRYPTO_IN_DB);
    }

    @Test
    void checkFindByIdShouldThrowException() {
        int expectedErrorCode = ExceptionCode.CRYPTO_NOT_FOUND.getErrorCode();
        EntityException actualException = assertThrows(EntityException.class, () -> service.findCryptoByCode(NOT_EXISTING_CRYPTO_CODE));
        assertThat(actualException.getErrorCode()).isEqualTo(expectedErrorCode);
    }

    @Test
    void checkFindByIdShouldReturnNotNull() {
        assertThat(service.findCryptoByCode(CRYPTO_CODE)).isNotNull();
    }
}
