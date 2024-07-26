package com.skerdy.httpulse.core.request;

import com.skerdy.httpulse.core.PulseRequest;
import org.springframework.stereotype.Component;

import java.net.http.HttpRequest;

@Component
public class GetRequestGenerator {

    public HttpRequest.Builder generate(HttpRequest.Builder httpRequestBuilder, PulseRequest pulseRequest) {
        return httpRequestBuilder.GET();
    }

}
