package com.skerdy.httpulse.core.exception;

public class UnsupportedHttpMethodException extends BaseException {

    public UnsupportedHttpMethodException(String httpMethod) {
        super(String.format("[%s] Http Method is not supported!", httpMethod));
    }
}
