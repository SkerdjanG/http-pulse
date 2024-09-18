package com.skerdy.httpulse.core.request;

import com.skerdy.httpulse.core.PulseRequest;
import com.skerdy.httpulse.core.exception.UnsupportedHttpMethodException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class HttpRequestGenerator implements RequestGenerator {

    private final PostRequestGenerator postRequestGenerator;
    private final GetRequestGenerator getRequestGenerator;
    private final PutRequestGenerator putRequestGenerator;
    private final PatchRequestGenerator patchRequestGenerator;
    private final DeleteRequestGenerator deleteRequestGenerator;

    @Override
    public HttpRequest generate(PulseRequest pulseRequest) {
        var httpRequest = HttpRequest.newBuilder(URI.create(pulseRequest.getUrl()));

        enrichHeaders(httpRequest, pulseRequest.getHeaders());

        switch(pulseRequest.getHttpMethod()) {
            case POST -> postRequestGenerator.generate(httpRequest, pulseRequest);
            case GET -> getRequestGenerator.generate(httpRequest, pulseRequest);
            case PUT -> putRequestGenerator.generate(httpRequest, pulseRequest);
            case PATCH -> patchRequestGenerator.generate(httpRequest, pulseRequest);
            case DELETE -> deleteRequestGenerator.generate(httpRequest, pulseRequest);
            default -> throw new UnsupportedHttpMethodException(pulseRequest.getHttpMethod().toString());
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
