package com.idfinance.repository.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"id", "priceUsd"})
@Table(name = "registered_crypto")
public class RegisteredCrypto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String symbol;

    @Column(name = "price")
    private BigDecimal priceUsd;

    @Column(name = "user_id")
    private Long userId;

    public RegisteredCrypto(CurrentCrypto crypto, User user){
        this.name = crypto.getName();
        this.priceUsd = crypto.getPriceUsd();
        this.symbol = crypto.getSymbol();
        this.userId = user.getId();
    }
}
