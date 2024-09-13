package com.skerdy.httpulse.manager.environment;

import com.skerdy.httpulse.core.exceptions.BaseException;

public class EnvironmentAlreadyExistsException extends BaseException {

    public EnvironmentAlreadyExistsException(String environmentName) {
        super("Failed to create. Environment: " + environmentName + " already exists!");
    }
}
