package com.idfinance.service.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AvailableCryptoUtil {
    @Value("${CryptoApi.cryptosId}")
    private String[] cryptosId;
    @Value("${CryptoApi.url}")
    private String apiUrl;

    public List<String> getUrlsForUpdateCryptos() {
        List<String> urls = new ArrayList<>();
        for (String str : cryptosId) {
            urls.add(apiUrl.concat(str));
        }
        return urls;
    }
}
