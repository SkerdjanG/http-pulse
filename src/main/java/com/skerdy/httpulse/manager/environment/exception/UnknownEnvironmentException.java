package com.skerdy.httpulse.manager.environment.exception;

import com.skerdy.httpulse.core.exception.BaseException;

public class UnknownEnvironmentException extends BaseException {

    public UnknownEnvironmentException(String environmentName) {
        super("Environment " + environmentName + " does not exist.");
    }
}
