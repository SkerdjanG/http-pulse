package com.skerdy.httpulse.manager.config;

import com.skerdy.httpulse.core.exceptions.BaseException;

public class InvalidPulseConfigurationException extends BaseException {

    public InvalidPulseConfigurationException(String message) {
        super(message);
    }
}
