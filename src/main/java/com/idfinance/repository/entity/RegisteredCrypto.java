package com.idfinance.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "registered_crypto")
public class RegisteredCrypto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String symbol;

    private BigDecimal priceUsd;

    @Column(name = "user_id")
    private Long userId;
}
