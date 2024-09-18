package com.skerdy.httpulse.core.exception;

public class HttpConnectionException extends BaseException {

    public HttpConnectionException(String url) {
        super(String.format("Unable to reach: %s . Make sure the url is correct!", url));
    }
}
