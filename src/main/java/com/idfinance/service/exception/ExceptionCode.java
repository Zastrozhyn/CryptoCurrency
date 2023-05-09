package com.idfinance.service.exception;

import lombok.ToString;

@ToString
public enum ExceptionCode {

    USER_NOT_FOUND(40201),
    NOT_VALID_USERNAME(40202),
    CRYPTO_NOT_FOUND(40101),
    CAN_NOT_READ_CRYPTO(40102);

    private final int errorCode;

    ExceptionCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
