package com.skerdy.httpulse.manager.environment.exception;

import com.skerdy.httpulse.core.exception.BaseException;

public class NoActiveEnvironmentException extends BaseException {

    public NoActiveEnvironmentException(String dataWithVariable) {
        super("There is no active environment provided, but there are variables defined at: " + dataWithVariable);
    }
}
