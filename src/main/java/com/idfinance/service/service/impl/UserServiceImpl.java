package com.idfinance.service.service.impl;

import com.idfinance.repository.dao.RegisteredCryptoRepository;
import com.idfinance.repository.dao.UserRepository;
import com.idfinance.repository.entity.CurrentCrypto;
import com.idfinance.repository.entity.RegisteredCrypto;
import com.idfinance.repository.entity.User;
import com.idfinance.service.exception.EntityException;
import com.idfinance.service.exception.ExceptionCode;
import com.idfinance.service.mapper.CurrentCryptoMapper;
import com.idfinance.service.service.CurrentCryptoService;
import com.idfinance.service.service.UserService;
import jakarta.annotation.PostConstruct;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final CurrentCryptoService currentCryptoService;
    private final RegisteredCryptoRepository registeredCryptoRepository;
    private final UserRepository userRepository;
    private final CurrentCryptoMapper mapper = Mappers.getMapper(CurrentCryptoMapper.class);
    private Set<User> userCache;

    @Autowired
    public UserServiceImpl(CurrentCryptoService currentCryptoService,
                           RegisteredCryptoRepository registeredCryptoRepository, UserRepository userRepository) {
        this.currentCryptoService = currentCryptoService;
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
        CurrentCrypto currentCrypto = mapper.mapToEntity(currentCryptoService.findCryptoByCode(code));
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
        System.out.println(userCache);
        return userRepository.findAll(pageable).getContent();
    }

    private List<User> findAllUser(){
        return userRepository.findAll();
    }
}
