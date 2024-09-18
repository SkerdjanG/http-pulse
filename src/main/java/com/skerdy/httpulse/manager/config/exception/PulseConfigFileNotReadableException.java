package com.skerdy.httpulse.manager.config.exception;

import com.skerdy.httpulse.core.exception.BaseException;

public class PulseConfigFileNotReadableException extends BaseException {

    public PulseConfigFileNotReadableException() {
        super("Could not read configuration file!");
    }
}
