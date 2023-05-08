package com.idfinance.service.util;

import com.idfinance.repository.entity.CurrentCrypto;
import com.idfinance.repository.entity.RegisteredCrypto;

public class CurrencyCalculator {

    public static float percentChanging(CurrentCrypto currentCrypto, RegisteredCrypto registeredCrypto){
        float diff = Math.abs(currentCrypto.getPriceUsd().floatValue() - registeredCrypto.getPriceUsd().floatValue());
        return  100 * diff / registeredCrypto.getPriceUsd().floatValue();
    }
}
