package com.skerdy.httpulse.core.request;

import com.skerdy.httpulse.core.PulseRequest;
import com.skerdy.httpulse.core.exceptions.UnsupportedHttpMethodException;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Map;

@Component
public class HttpRequestGenerator {

    private final PostRequestGenerator postRequestGenerator;
    private final GetRequestGenerator getRequestGenerator;
    private final PutRequestGenerator putRequestGenerator;
    private final PatchRequestGenerator patchRequestGenerator;
    private final DeleteRequestGenerator deleteRequestGenerator;

    public HttpRequestGenerator(PostRequestGenerator postRequestGenerator,
                                GetRequestGenerator getRequestGenerator,
                                PutRequestGenerator putRequestGenerator,
                                PatchRequestGenerator patchRequestGenerator,
                                DeleteRequestGenerator deleteRequestGenerator) {
        this.postRequestGenerator = postRequestGenerator;
        this.getRequestGenerator = getRequestGenerator;
        this.putRequestGenerator = putRequestGenerator;
        this.patchRequestGenerator = patchRequestGenerator;
        this.deleteRequestGenerator = deleteRequestGenerator;
    }

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
