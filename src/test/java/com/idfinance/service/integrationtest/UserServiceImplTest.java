package com.idfinance.service.integrationtest;

import com.idfinance.repository.entity.User;
import com.idfinance.service.exception.EntityException;
import com.idfinance.service.exception.ExceptionCode;
import com.idfinance.service.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserServiceImplTest extends BaseIntegrationTest {

    private static final String NAME = "qwerty";
    private static final String CRYPTO_CODE = "BTC";
    private static final long EXISTING_USER_ID = 2;
    private static final long NON_EXISTING_USER_ID = 100;
    private static final int PAGE = 0;
    private static final int PAGE_SIZE = 10;
    private static final Integer AMOUNT_OF_USER_IN_DB = 3;

    private final UserService service;

    @Autowired
    UserServiceImplTest(UserService service) {
        this.service = service;
    }

    @Test
    void findAllShouldReturn3() {
        List<User> users = service.findAllUser(PageRequest.of(PAGE, PAGE_SIZE));
        assertThat(users).hasSize(AMOUNT_OF_USER_IN_DB);
    }

    @Test
    void checkFindByIdShouldThrowException() {
        int expectedErrorCode = ExceptionCode.USER_NOT_FOUND.getErrorCode();
        EntityException actualException = assertThrows(EntityException.class, () -> service.findUserById(NON_EXISTING_USER_ID));
        assertThat(actualException.getErrorCode()).isEqualTo(expectedErrorCode);
    }

    @Test
    void checkFindById(){
        User user = service.findUserById(EXISTING_USER_ID);
        assertThat(user).isNotNull();
    }

    @Test
    void checkRegisterUSer() {
        assertThat(service.findAllUser(PageRequest.of(PAGE, PAGE_SIZE))).hasSize(AMOUNT_OF_USER_IN_DB);
        service.registerUser(NAME, CRYPTO_CODE);
        assertThat(service.findAllUser(PageRequest.of(PAGE, PAGE_SIZE))).hasSize(AMOUNT_OF_USER_IN_DB + 1);
    }

}