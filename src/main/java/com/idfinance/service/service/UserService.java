package com.idfinance.service.service;

import com.idfinance.repository.entity.CurrentCrypto;
import com.idfinance.repository.entity.RegisteredCrypto;
import com.idfinance.repository.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    RegisteredCrypto registerUser(String userName, String code);

    User findUserById(Long userId);

    List<User> findAllUser(Pageable pageable);

    void alertCurrencyChanging(CurrentCrypto currentCrypto);
}
