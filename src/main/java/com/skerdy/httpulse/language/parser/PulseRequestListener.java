package com.skerdy.httpulse.language.parser;

import com.skerdy.httpulse.PulseRequestBaseListener;
import com.skerdy.httpulse.PulseRequestParser;
import com.skerdy.httpulse.language.model.RawPulseRequest;

import java.util.*;

public class PulseRequestListener extends PulseRequestBaseListener {

    private List<RawPulseRequest> requests;

    private RawPulseRequest current;

    private Map<String, String> headers;

    private Map.Entry<String, String> header;

    public List<RawPulseRequest> getRequests() {
        return requests;
    }

    @Override
    public void enterRequestFile(PulseRequestParser.RequestFileContext ctx) {
        requests = new ArrayList<>();
    }

    @Override
    public void enterRequest(PulseRequestParser.RequestContext ctx) {
        current = new RawPulseRequest();
    }

    @Override
    public void exitRequest(PulseRequestParser.RequestContext ctx) {
        if (current != null) {
            requests.add(current);
        }
    }

    @Override
    public void enterRequestName(PulseRequestParser.RequestNameContext ctx) {
        current.setName(ctx.REQUEST_NAME().getText());
    }

    @Override
    public void enterHttpMethod(PulseRequestParser.HttpMethodContext ctx) {
        current.setHttpMethod(ctx.getText());
    }

    @Override
    public void enterUrl(PulseRequestParser.UrlContext ctx) {
        current.setUrl(ctx.getText());
    }

    @Override
    public void enterHeaders(PulseRequestParser.HeadersContext ctx) {
        headers = new HashMap<>();
    }

    @Override
    public void exitHeaders(PulseRequestParser.HeadersContext ctx) {
        current.setHeaders(headers);
    }

    @Override
    public void enterHeader(PulseRequestParser.HeaderContext ctx) {
        // do nothing
    }

    @Override
    public void exitHeader(PulseRequestParser.HeaderContext ctx) {
        headers.put(header.getKey(), header.getValue());
    }

    @Override
    public void enterHeaderName(PulseRequestParser.HeaderNameContext ctx) {
        header = new AbstractMap.SimpleEntry<>(ctx.getText(), "");
    }

    @Override
    public void enterHeaderValue(PulseRequestParser.HeaderValueContext ctx) {
        header.setValue(ctx.getText());
    }

    @Override
    public void enterBody(PulseRequestParser.BodyContext ctx) {
        current.setBody(ctx.getText()
                .replace("<EOF>", "")
                .replace("\"\"", "\"")
        );
    }
}
