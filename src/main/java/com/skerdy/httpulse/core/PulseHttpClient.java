package com.skerdy.httpulse.core;

import com.skerdy.httpulse.core.exceptions.HttpConnectionException;
import com.skerdy.httpulse.core.request.HttpRequestGenerator;
import com.skerdy.httpulse.mapping.PrintableMapper;
import com.skerdy.httpulse.terminal.writer.TerminalPrettyWriter;
import com.skerdy.httpulse.terminal.writer.model.PrintableReceiveResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class PulseHttpClient {

    private final HttpClient httpClient;

    private final HttpRequestGenerator httpRequestGenerator;

    private final TerminalPrettyWriter terminalPrettyWriter;

    private final PrintableMapper printableMapper;

    public PulseHttpClient(HttpRequestGenerator httpRequestGenerator,
                           TerminalPrettyWriter terminalPrettyWriter,
                           PrintableMapper printableMapper) {
        this.httpRequestGenerator = httpRequestGenerator;
        this.terminalPrettyWriter = terminalPrettyWriter;
        this.printableMapper = printableMapper;
        httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    public void execute(PulseRequest pulseRequest) {
        HttpRequest httpRequest = httpRequestGenerator.generate(pulseRequest);
        try {
            // print the event of sending request in the terminal
            printRequest(pulseRequest);

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            // print the event of receiving response in the terminal
            printResponse(response);
        } catch (IOException | InterruptedException e) {
            throw new HttpConnectionException(pulseRequest.getUrl());
        }
    }

    private void printRequest(PulseRequest pulseRequest) {
        var printableSendRequest = printableMapper.printableSendRequest(pulseRequest);
        terminalPrettyWriter.printSendRequest(printableSendRequest);
    }

    private void printResponse(HttpResponse<String> response) {
        var printableReceiveResponse = new PrintableReceiveResponse();
        printableReceiveResponse.setBody(response.body());
        printableReceiveResponse.setStatusCode(response.statusCode());
        terminalPrettyWriter.printReceiveResponse(printableReceiveResponse);
    }

}
