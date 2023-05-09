package com.idfinance.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "crypto_current")
public class CurrentCrypto implements BaseEntity<Long> {

    @Id
    private Long id;

    private String name;

    private String symbol;

    @Column(name = "price")
    private BigDecimal priceUsd;
}
