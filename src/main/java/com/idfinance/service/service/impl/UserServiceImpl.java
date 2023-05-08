package com.idfinance.service.service.impl;

import com.idfinance.repository.dao.CurrentCryptoRepository;
import com.idfinance.repository.dao.RegisteredCryptoRepository;
import com.idfinance.repository.dao.UserRepository;
import com.idfinance.repository.entity.CurrentCrypto;
import com.idfinance.repository.entity.RegisteredCrypto;
import com.idfinance.repository.entity.User;
import com.idfinance.service.exception.EntityException;
import com.idfinance.service.exception.ExceptionCode;
import com.idfinance.service.service.UserService;
import com.idfinance.service.util.CurrencyCalculator;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private final CurrentCryptoRepository currentCryptoRepository;
    private final RegisteredCryptoRepository registeredCryptoRepository;
    private final UserRepository userRepository;
    private Set<User> userCache;
    @Value("${Currency.percentChangingToAlert}")
    private float percentChanging;

    @Autowired
    public UserServiceImpl(CurrentCryptoRepository currentCryptoRepository,
                           RegisteredCryptoRepository registeredCryptoRepository, UserRepository userRepository) {
        this.currentCryptoRepository = currentCryptoRepository;
        this.registeredCryptoRepository = registeredCryptoRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    private void init(){
        userCache = new HashSet<>(findAllUser());
    }

    @Override
    @Transactional
    public RegisteredCrypto registerUser(String userName, String code) {
        User user = userRepository.findUserByName(userName).orElseGet(() -> userRepository.save(new User(userName)));
        CurrentCrypto currentCrypto = currentCryptoRepository.findBySymbolContainingIgnoreCase(code)
                .orElseThrow(() -> new EntityException(ExceptionCode.CRYPTO_NOT_FOUND.getErrorCode()));
        RegisteredCrypto registeredCrypto = new RegisteredCrypto(currentCrypto, user);
        if (user.getCryptoRegisteredSet().contains(registeredCrypto)){
            registeredCryptoRepository.deleteBySymbolAndUserId(registeredCrypto.getSymbol(), user.getId());
        }
        RegisteredCrypto result = registeredCryptoRepository.save(new RegisteredCrypto(currentCrypto, user));
        userCache.add(findUserById(user.getId()));
        return result;
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityException(ExceptionCode.USER_NOT_FOUND.getErrorCode()));
    }

    @Override
    public List<User> findAllUser(Pageable pageable) {
        return userRepository.findAll(pageable).getContent();
    }

    @Override
    public void alertCurrencyChanging(CurrentCrypto currentCrypto){
        for(User user: userCache){
            user.getCryptoRegisteredSet().stream()
                    .filter(crypto -> crypto.getSymbol().equals(currentCrypto.getSymbol()))
                    .filter(crypto -> isCurrencyChangedMoreThenSpecified(currentCrypto, crypto))
                    .forEach(crypto -> alert(user, currentCrypto, crypto));
        }
    }

    private List<User> findAllUser(){
        return userRepository.findAll();
    }

    private boolean isCurrencyChangedMoreThenSpecified(CurrentCrypto currentCrypto, RegisteredCrypto registeredCrypto){
        return CurrencyCalculator.percentChanging(currentCrypto, registeredCrypto) > percentChanging;
    }

    private void alert(User user, CurrentCrypto currentCrypto, RegisteredCrypto registeredCrypto){
        float percentChanging = CurrencyCalculator.percentChanging(currentCrypto, registeredCrypto);
        log.warn(user.getName() + ": " + registeredCrypto.getSymbol() + ": current price=" + currentCrypto.getPriceUsd()
                + ", registered price= " + registeredCrypto.getPriceUsd() + " %=" + percentChanging);
    }
}
