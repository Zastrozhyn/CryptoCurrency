package com.idfinance.repository.entity;

import java.io.Serializable;

public interface BaseEntity<K extends Serializable> {
    K getId();
}
