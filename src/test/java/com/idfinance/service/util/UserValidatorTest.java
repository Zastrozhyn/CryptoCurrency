package com.idfinance.service.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserValidatorTest {
    private static final String NOT_VALID_NAME = "a";
    private final UserValidator validator;

    @Autowired
    public UserValidatorTest(UserValidator validator) {
        this.validator = validator;
    }

    @ParameterizedTest
    @ValueSource(strings = {"Name", "User-name", "another-user-name", "QWERTY", "qaw"})
    void checkIsUserNameValidShouldReturnTrue(String userName) {
        assertThat(validator.isUserNameValid(userName)).isTrue();
    }

    @Test
    void checkIsUserNameValidShouldReturnFalse(){
        assertThat(validator.isUserNameValid(NOT_VALID_NAME)).isFalse();
    }
}