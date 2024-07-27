package com.skerdy.httpulse.mapping;

import com.skerdy.httpulse.core.PulseRequest;
import com.skerdy.httpulse.terminal.writer.model.PrintableSendRequest;
import org.springframework.stereotype.Component;

@Component
public class PrintableMapper {

    public PrintableSendRequest printableSendRequest(PulseRequest pulseRequest) {
        var result = new PrintableSendRequest();
        result.setUrl(pulseRequest.getUrl());
        result.setHeaders(pulseRequest.getHeaders());
        result.setHttpMethod(pulseRequest.getHttpMethod());
        result.setBody(pulseRequest.getBody());
        return result;
    }

}
