package com.skerdy.httpulse.command.environment;

import com.skerdy.httpulse.manager.environment.EnvironmentManager;
import com.skerdy.httpulse.terminal.writer.TerminalPrettyWriter;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ListEnvironmentsCommand {

    private final EnvironmentManager environmentManager;
    private final TerminalPrettyWriter terminalPrettyWriter;

    public ListEnvironmentsCommand(EnvironmentManager environmentManager,
                                   TerminalPrettyWriter terminalPrettyWriter) {
        this.environmentManager = environmentManager;
        this.terminalPrettyWriter = terminalPrettyWriter;
    }

    public void listEnvironments() {
        var environments = environmentManager.listEnvironments();
        var activeEnvironment = environmentManager.getActiveEnvironment();
        var environmentsFileLocation = environmentManager.getEnvironmentsFileLocation();
        terminalPrettyWriter.print(styleListEnvironments(environments, activeEnvironment, environmentsFileLocation));
    }

    private String styleListEnvironments(Set<String> environments,
                                         String activeEnvironment,
                                         String environmentsFileLocation) {
        if (environments.isEmpty()) {
            return styleEmptyEnvironments();
        }
        boolean addComma = false;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                new AttributedStringBuilder()
                    .style(AttributedStyle.DEFAULT.bold())
                    .append("The following environments were found at: ")
                    .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE).italic())
                    .append(environmentsFileLocation)
                    .append(System.lineSeparator())
                    .toAnsi()
        );
        for (String environment : environments) {
            stringBuilder.append(styleEnvironment(environment, addComma, environment.equals(activeEnvironment)));
            if (!addComma) {
                addComma = true;
            }
        }
        return stringBuilder.toString();
    }

    private String styleEnvironment(String environment, boolean addComma, boolean isActive) {
        var result = new AttributedStringBuilder();
        if (isActive) {
            result.style(AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN).bold());
        } else {
            result.style(AttributedStyle.DEFAULT.italic());
        }

        if (addComma) {
            result.append(", ");
        }
        result.append(environment);
        return result.toAnsi();
    }

    private String styleEmptyEnvironments() {
        return new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.bold())
                .append("No environments were found. Try to add one via -> new env <envName>.")
                .toAnsi();
    }
}
