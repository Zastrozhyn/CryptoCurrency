package com.idfinance.web.controller;


import com.idfinance.repository.entity.RegisteredCrypto;
import com.idfinance.repository.entity.User;
import com.idfinance.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegisteredCrypto registerUser(@RequestParam(name = "userName") String userName,
                                         @RequestParam(name = "code") String code) {
        return userService.registerUser(userName, code);
    }

    @GetMapping("/{userId}")
    public User findUserById(@PathVariable Long userId) {
        return userService.findUserById(userId);
    }

    @GetMapping()
    public List<User> findAllUser(Pageable pageable) {
        return userService.findAllUser(pageable);
    }
}
