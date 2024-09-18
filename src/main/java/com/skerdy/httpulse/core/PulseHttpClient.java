package com.skerdy.httpulse.core;

import com.skerdy.httpulse.core.exception.HttpConnectionException;
import com.skerdy.httpulse.core.request.RequestGenerator;
import com.skerdy.httpulse.mapping.PrintableMapper;
import com.skerdy.httpulse.terminal.writer.TerminalPrettyWriter;
import com.skerdy.httpulse.terminal.writer.model.PrintableReceiveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
@RequiredArgsConstructor
public class PulseHttpClient {

    private final HttpClient httpClient;

    private final RequestGenerator httpRequestGenerator;

    private final TerminalPrettyWriter terminalPrettyWriter;

    private final PrintableMapper printableMapper;

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
