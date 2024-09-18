package com.skerdy.httpulse.manager.config.exception;

import com.skerdy.httpulse.core.exception.BaseException;

public class SampleDirectoryCreationException extends BaseException {

    public SampleDirectoryCreationException() {
        super("Failed to create sample directory successfully!");
    }
}
