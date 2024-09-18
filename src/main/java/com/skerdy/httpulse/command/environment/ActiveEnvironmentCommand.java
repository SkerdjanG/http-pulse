package com.skerdy.httpulse.command.environment;

import com.skerdy.httpulse.manager.api.ApiManager;
import com.skerdy.httpulse.manager.config.ConfigManager;
import com.skerdy.httpulse.manager.environment.EnvironmentManager;
import com.skerdy.httpulse.manager.environment.exception.UnknownEnvironmentException;
import com.skerdy.httpulse.terminal.writer.TerminalPrettyWriter;
import lombok.RequiredArgsConstructor;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ActiveEnvironmentCommand {

    private final EnvironmentManager environmentManager;
    private final ApiManager apiManager;
    private final ConfigManager configManager;
    private final TerminalPrettyWriter terminalPrettyWriter;

    public void useEnvironment(String environmentName) {
        try {
            environmentManager.useEnvironment(environmentName);
            terminalPrettyWriter.print(styleUseEnvironmentSuccessful(environmentName));
            apiManager.init(configManager.getPulseConfiguration(), false);
        } catch (UnknownEnvironmentException unknownEnvironmentException) {
            var allowedEnvironments = environmentManager.listEnvironments();
            terminalPrettyWriter.print(styleUnknownEnvironmentException(unknownEnvironmentException, allowedEnvironments));
        }
    }

    private String styleUseEnvironmentSuccessful(String environmentName) {
        return new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.italic())
                .append("Using ").append(environmentName).append(" environment.")
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
