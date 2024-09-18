package com.skerdy.httpulse.language.parser;

import com.skerdy.httpulse.PulseRequestLexer;
import com.skerdy.httpulse.PulseRequestParser;
import com.skerdy.httpulse.language.model.RawPulseRequest;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.List;

public class PulseParser implements Parser {

    @Override
    public List<RawPulseRequest> parse(String text) {
        var pulseLexer = new PulseRequestLexer(CharStreams.fromString(text));
        var tokens = new CommonTokenStream(pulseLexer);
        PulseRequestParser pulseRequestParser = new PulseRequestParser(tokens);
        pulseRequestParser.removeErrorListener(ConsoleErrorListener.INSTANCE);
        pulseRequestParser.addErrorListener(ParseErrorListener.INSTANCE);
        ParseTreeWalker walker = new ParseTreeWalker();
        PulseRequestListener pulseRequestListener = new PulseRequestListener();
        walker.walk(pulseRequestListener, pulseRequestParser.requestFile());
        return pulseRequestListener.getRequests();
    }
}
