package com.idfinance.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = "cryptoRegisteredSet")
@ToString
@Table(name = "users")
public class User implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Set<RegisteredCrypto> cryptoRegisteredSet = new HashSet<>();

    public User(String name) {
        this.name = name;
    }
}
