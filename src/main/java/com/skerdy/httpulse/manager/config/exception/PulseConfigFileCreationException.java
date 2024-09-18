package com.skerdy.httpulse.manager.config.exception;

import com.skerdy.httpulse.core.exception.BaseException;

import java.io.File;

public class PulseConfigFileCreationException extends BaseException {

    public PulseConfigFileCreationException() {
        super("Failed to create config file under " + System.getProperty("user.home") + File.separator + ".pulse" + " directory.");
    }
}
