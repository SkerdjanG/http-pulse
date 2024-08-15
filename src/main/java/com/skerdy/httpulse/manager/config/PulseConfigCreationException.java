package com.skerdy.httpulse.manager.config;

import com.skerdy.httpulse.core.exceptions.BaseException;

public class PulseConfigCreationException extends BaseException {

    public PulseConfigCreationException() {
        super("Failed to create .pulse directory under " + System.getProperty("user.home") + " directory.");
    }
}
