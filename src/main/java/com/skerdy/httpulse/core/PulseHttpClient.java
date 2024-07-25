package com.skerdy.httpulse.core;

import com.skerdy.httpulse.core.exceptions.HttpConnectionException;
import com.skerdy.httpulse.core.request.HttpRequestGenerator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class PulseHttpClient {

    private final HttpClient httpClient;

    private final HttpRequestGenerator httpRequestGenerator;

    public PulseHttpClient(HttpRequestGenerator httpRequestGenerator) {
        this.httpRequestGenerator = httpRequestGenerator;
        httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    public String execute(PulseRequest pulseRequest) {
        HttpRequest httpRequest = httpRequestGenerator.generate(pulseRequest);
        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new HttpConnectionException(pulseRequest.getUrl());
        }
    }
}
