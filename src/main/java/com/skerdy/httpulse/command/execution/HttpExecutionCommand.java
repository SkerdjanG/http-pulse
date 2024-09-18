package com.skerdy.httpulse.command.execution;

import com.skerdy.httpulse.core.PulseHttpClient;
import com.skerdy.httpulse.manager.api.ApiManager;
import com.skerdy.httpulse.terminal.writer.TerminalPrettyWriter;
import lombok.RequiredArgsConstructor;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HttpExecutionCommand {

    private final TerminalPrettyWriter terminalPrettyWriter;
    private final PulseHttpClient pulseHttpClient;
    private final ApiManager apiManager;

    public void executeHttpRequest(int requestIndex) {
        var pulseRequests = apiManager.getRequests();
        if (pulseRequests == null || pulseRequests.isEmpty()) {
            terminalPrettyWriter.print(styleNoRequestsAvailable());
        } else {
            if (requestIndex >= 0 && requestIndex <= pulseRequests.size() - 1) {
                var pulseRequest = apiManager.getRequest(requestIndex);
                pulseHttpClient.execute(pulseRequest);
            } else {
                terminalPrettyWriter.print(styleInvalidIdentifierOfRequests(requestIndex, pulseRequests.size()));
            }
        }
    }

    private String styleNoRequestsAvailable() {
        return new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.RED).bold())
                .append("There are no requests in this context.")
                .append(System.lineSeparator())
                .append("Please consider to initialise a new context with a valid .pulse file containing requests.")
                .toAnsi();
    }

    private String styleInvalidIdentifierOfRequests(int index, int size) {
        return new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.RED).bold())
                .append("Invalid identifier passed: ").append("").append(String.valueOf(index))
                .append(System.lineSeparator())
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.RED).italic())
                .append("Index should be >= 0 AND <= ").append(String.valueOf(size-1))
                .append(" to successfully identify a request.")
                .toAnsi();
    }
}
