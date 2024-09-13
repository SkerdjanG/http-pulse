package com.skerdy.httpulse.command.environment;

import com.skerdy.httpulse.manager.environment.EnvironmentManager;
import com.skerdy.httpulse.manager.environment.UnknownEnvironmentException;
import com.skerdy.httpulse.terminal.writer.TerminalPrettyWriter;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ShowEnvironmentCommand {

    private final EnvironmentManager environmentManager;
    private final TerminalPrettyWriter terminalPrettyWriter;

    public ShowEnvironmentCommand(EnvironmentManager environmentManager,
                                  TerminalPrettyWriter terminalPrettyWriter) {
        this.environmentManager = environmentManager;
        this.terminalPrettyWriter = terminalPrettyWriter;
    }

    public void showEnvironment(String environmentName) {
        try {
            var environmentJsonString = environmentManager.showEnvironment(environmentName);
            terminalPrettyWriter.print(styleShowEnvironmentSuccessful(environmentJsonString));
        } catch (UnknownEnvironmentException unknownEnvironmentException) {
            var allowedEnvironments = environmentManager.listEnvironments();
            terminalPrettyWriter.print(styleUnknownEnvironmentException(unknownEnvironmentException, allowedEnvironments));
        }
    }

    private String styleShowEnvironmentSuccessful(String environmentJsonString) {
        return new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.italic())
                .append(environmentJsonString)
                .toAnsi();
    }

    private String styleUnknownEnvironmentException(UnknownEnvironmentException unknownEnvironmentException,
                                                    Set<String> allowedEnvironments) {
        return new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.RED).bold())
                .append(unknownEnvironmentException.getMessage())
                .append(System.lineSeparator())
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE).bold())
                .append("Please use an existing environment: ")
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN).italic())
                .append(String.join(",", allowedEnvironments))
                .toAnsi();
    }
}
