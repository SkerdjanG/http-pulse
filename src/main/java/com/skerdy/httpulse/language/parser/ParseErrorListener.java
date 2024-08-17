package com.skerdy.httpulse.language.parser;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class ParseErrorListener extends BaseErrorListener {

    public static final ParseErrorListener INSTANCE = new ParseErrorListener();

    private ParseErrorListener() {}

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        throw new PulseParseException(offendingSymbol, line, charPositionInLine, msg);
    }
}
