package com.skerdy.httpulse.manager.config;

import com.skerdy.httpulse.core.exceptions.BaseException;

import java.io.File;

public class PulseConfigFileCreationException extends BaseException {

    public PulseConfigFileCreationException() {
        super("Failed to create config file under " + System.getProperty("user.home") + File.separator + ".pulse" + " directory.");
    }
}
