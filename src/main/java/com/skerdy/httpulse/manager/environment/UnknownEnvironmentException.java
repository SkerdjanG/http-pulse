package com.skerdy.httpulse.manager.environment;

import com.skerdy.httpulse.core.exceptions.BaseException;

public class UnknownEnvironmentException extends BaseException {

    public UnknownEnvironmentException(String environmentName) {
        super("Environment " + environmentName + " does not exist.");
    }
}
