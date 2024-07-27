package com.skerdy.httpulse.terminal.writer;

import com.skerdy.httpulse.core.HttpMethod;
import com.skerdy.httpulse.terminal.writer.model.PrintableReceiveResponse;
import com.skerdy.httpulse.terminal.writer.model.PrintableSendRequest;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TerminalPrettyWriter {

    private final Terminal terminal;

    private final PrettyJsonify prettyJsonify;

    public TerminalPrettyWriter(Terminal terminal, PrettyJsonify prettyJsonify) {
        this.terminal = terminal;
        this.prettyJsonify = prettyJsonify;
    }

    public void printSendRequest(PrintableSendRequest printableSendRequest) {
        var writer = terminal.writer();

        writer.println(printSendingRequestMessage());
        writer.println(printMethodAndUrl(printableSendRequest.getHttpMethod(), printableSendRequest.getUrl()));

        if (printableSendRequest.getHeaders() != null && !printableSendRequest.getHeaders().isEmpty()) {
            writer.println(printHeaders(printableSendRequest.getHeaders()));
        }

        if (printableSendRequest.getBody() != null && !printableSendRequest.getBody().isEmpty()) {
            writer.println(printBody(printableSendRequest.getBody()));
        }

        writer.flush();
    }

    public void printReceiveResponse(PrintableReceiveResponse printableReceiveResponse) {
        var writer = terminal.writer();

        writer.println();
        writer.println(printReceivedResponse(printableReceiveResponse.getStatusCode()));

        if (printableReceiveResponse.getBody() != null && !printableReceiveResponse.getBody().isEmpty()) {
            writer.println(printResponseBody(printableReceiveResponse.getBody()));
        }

        writer.flush();
    }

    /**
     * Methods for Sending Request
     */

    private String printSendingRequestMessage() {
        return new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.WHITE).blink())
                .append(System.lineSeparator())
                .append("Sending request...")
                .toAnsi();
    }

    private String printMethodAndUrl(HttpMethod httpMethod, String url) {
        return printMethod(httpMethod) + printUrl(url);
    }

    private String printMethod(HttpMethod httpMethod) {
        int color = AttributedStyle.WHITE;

        if (httpMethod.equals(HttpMethod.POST)) {
            color = AttributedStyle.GREEN;
        } else if (httpMethod.equals(HttpMethod.GET)) {
            color = AttributedStyle.BLUE;
        } else if (httpMethod.equals(HttpMethod.PUT)) {
            color = AttributedStyle.YELLOW;
        }
        return new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.foreground(color))
                .append(httpMethod.name())
                .style(AttributedStyle.DEFAULT)
                .append(" -> ")
                .toAnsi();
    }

    private String printUrl(String url) {
        return new AttributedStringBuilder()
                .style(AttributedStyle.BOLD)
                .append(url)
                .toAnsi();
    }

    private String printHeaders(Map<String, String> headers) {
        StringBuilder result = new StringBuilder();
        for(Map.Entry<String, String> header : headers.entrySet()) {
            result.append(printHeader(header.getKey(), header.getValue()));
        }
        return result.toString();
    }

    private String printHeader(String headerName, String headerValue) {
        return new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN).italic())
                .append(headerName)
                .append(": ")
                .append(headerValue)
                .toAnsi();
    }

    private String printBody(String body) {
        return new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW))
                .append(prettyJsonify.pretty(body))
                .toAnsi();
    }

    /**
     * Methods for Receiving Response
     */

    private String printReceivedResponse(int responseCode) {
        return new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.WHITE))
                .append("Received response: ")
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN).bold())
                .append("code = ")
                .append(String.valueOf(responseCode))
                .toAnsi();
    }

    private String printResponseBody(String responseBody) {
        return new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN))
                .append(prettyJsonify.pretty(responseBody))
                .toAnsi();
    }

}
