package com.skerdy.httpulse.command.environment;

import com.skerdy.httpulse.manager.environment.exception.EnvironmentAlreadyExistsException;
import com.skerdy.httpulse.manager.environment.EnvironmentManager;
import com.skerdy.httpulse.terminal.writer.TerminalPrettyWriter;
import lombok.RequiredArgsConstructor;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewEnvironmentCommand {

    private final EnvironmentManager environmentManager;
    private final TerminalPrettyWriter terminalPrettyWriter;

    public void newEnvironment(String environmentName) {
        try {
            environmentManager.createNewEnvironment(environmentName);
            var environmentsFileLocation = environmentManager.getEnvironmentsFileLocation();
            terminalPrettyWriter.print(styleEnvironmentAddedSuccessfully(environmentName, environmentsFileLocation));
        } catch (EnvironmentAlreadyExistsException environmentAlreadyExistsException) {
            terminalPrettyWriter.print(styleEnvironmentAlreadyExistsException(environmentAlreadyExistsException));
        }
    }

    private String styleEnvironmentAddedSuccessfully(String newEnvironment, String environmentsFileLocation) {
        return new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.bold())
                .append("Environment ").append(newEnvironment).append(" added successfully.")
                .append(System.lineSeparator())
                .append("Manage your environments at: ")
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE).italic())
                .append(environmentsFileLocation)
                .toAnsi();
    }

    private String styleEnvironmentAlreadyExistsException(EnvironmentAlreadyExistsException environmentAlreadyExistsException) {
        return new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.RED).bold())
                .append(environmentAlreadyExistsException.getMessage())
                .append(System.lineSeparator())
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE).bold())
                .append("No changes have been performed.")
                .toAnsi();
    }
}
