package com.skerdy.httpulse.manager.config.exception;

import com.skerdy.httpulse.core.exception.BaseException;

public class PulseConfigCreationException extends BaseException {

    public PulseConfigCreationException() {
        super("Failed to create .pulse directory under " + System.getProperty("user.home") + " directory.");
    }
}
