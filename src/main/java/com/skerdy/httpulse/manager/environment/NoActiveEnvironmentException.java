package com.skerdy.httpulse.manager.environment;

import com.skerdy.httpulse.core.exceptions.BaseException;

public class NoActiveEnvironmentException extends BaseException {

    public NoActiveEnvironmentException(String dataWithVariable) {
        super("There is no active environment provided, but there are variables defined at: " + dataWithVariable);
    }
}
