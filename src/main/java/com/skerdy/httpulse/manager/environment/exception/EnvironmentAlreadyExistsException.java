package com.skerdy.httpulse.manager.environment.exception;

import com.skerdy.httpulse.core.exception.BaseException;

public class EnvironmentAlreadyExistsException extends BaseException {

    public EnvironmentAlreadyExistsException(String environmentName) {
        super("Failed to create. Environment: " + environmentName + " already exists!");
    }
}
