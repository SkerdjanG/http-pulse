package com.skerdy.httpulse.language.parser;

import com.skerdy.httpulse.core.exceptions.BaseException;

public class PulseParseException extends BaseException {

    private static final String ERROR_MESSAGE = "Failed to parse -> line: %d, charPosition: %d, Offending symbol: %s, message: %s";

    public PulseParseException(Object offendingSymbol, int line, int charPositionInLine, String message) {
        super(String.format(ERROR_MESSAGE, line, charPositionInLine, offendingSymbol.toString(), message));
    }
}
