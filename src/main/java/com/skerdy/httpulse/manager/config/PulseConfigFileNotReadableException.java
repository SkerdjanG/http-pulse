package com.skerdy.httpulse.manager.config;

import com.skerdy.httpulse.core.exceptions.BaseException;

public class PulseConfigFileNotReadableException extends BaseException {

    public PulseConfigFileNotReadableException() {
        super("Could not read configuration file!");
    }
}
