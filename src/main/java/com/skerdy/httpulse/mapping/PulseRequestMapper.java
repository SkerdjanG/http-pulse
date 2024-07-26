package com.skerdy.httpulse.mapping;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.skerdy.httpulse.core.HttpMethod;
import com.skerdy.httpulse.core.PulseRequest;
import com.skerdy.httpulse.language.model.RawPulseRequest;
import org.springframework.stereotype.Component;

@Component
public class PulseRequestMapper {

    public PulseRequest fromRawPulseRequest(RawPulseRequest rawPulseRequest) {
        var result = new PulseRequest();

        result.setHttpMethod(HttpMethod.valueOf(rawPulseRequest.getHttpMethod()));
        result.setUrl(rawPulseRequest.getUrl());
        result.setHeaders(rawPulseRequest.getHeaders());
        if (rawPulseRequest.getBody() != null) {
            JsonObject body = JsonParser.parseString(rawPulseRequest.getBody())
                    .getAsJsonObject();
            result.setBody(body.toString());
        }
        return result;
    }

}

