package com.skerdy.httpulse.core.request;

import com.skerdy.httpulse.core.PulseRequest;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Map;

@Component
public class HttpRequestGenerator {

    private final PostRequestGenerator postRequestGenerator;

    public HttpRequestGenerator(PostRequestGenerator postRequestGenerator) {
        this.postRequestGenerator = postRequestGenerator;
    }

    public HttpRequest generate(PulseRequest pulseRequest) {
        var httpRequest = HttpRequest.newBuilder(URI.create(pulseRequest.getUrl()));

        enrichHeaders(httpRequest, pulseRequest.getHeaders());

        switch(pulseRequest.getHttpMethod()) {
            case POST -> postRequestGenerator.generate(httpRequest, pulseRequest);
            default -> throw new RuntimeException();
        }
        return httpRequest.build();
    }

    private void enrichHeaders(HttpRequest.Builder httpRequestBuilder, Map<String, String> headers) {
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                httpRequestBuilder.header(header.getKey(), header.getValue());
            }
        }
    }

}
