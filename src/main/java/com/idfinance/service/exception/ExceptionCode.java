package com.idfinance.service.exception;

public enum ExceptionCode {
    CRYPTO_NOT_FOUND(40101);

    private final int errorCode;

    ExceptionCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
