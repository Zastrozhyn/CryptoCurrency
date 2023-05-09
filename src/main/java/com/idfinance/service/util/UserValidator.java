package com.idfinance.service.util;

import com.idfinance.repository.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public static final int MAX_USER_NAME_LENGTH = 50;
    public static final int MIN_USER_NAME_LENGTH = 3;

    public boolean isUserNameValid(String name) {
        return name != null && name.length() >= MIN_USER_NAME_LENGTH && name.length() <= MAX_USER_NAME_LENGTH;
    }
}
