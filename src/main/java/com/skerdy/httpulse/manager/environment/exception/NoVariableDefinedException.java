package com.skerdy.httpulse.manager.environment.exception;

import com.skerdy.httpulse.core.exception.BaseException;

public class NoVariableDefinedException extends BaseException {

    public NoVariableDefinedException(String environment, String variable) {
        super("No definition found for variable -> " + variable + " in environment -> " + environment + "!");
    }
}
