package com.skerdy.httpulse.command.environment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnvironmentCommandsWrapper {

    private final NewEnvironmentCommand newEnvironmentCommand;
    private final ListEnvironmentsCommand listEnvironmentsCommand;
    private final ActiveEnvironmentCommand activeEnvironmentCommand;
    private final ShowEnvironmentCommand showEnvironmentCommand;

    public void newEnvironment(String environmentName) {
        newEnvironmentCommand.newEnvironment(environmentName);
    }

    public void listEnvironments() {
        listEnvironmentsCommand.listEnvironments();
    }

    public void useEnvironment(String environmentName) {
        activeEnvironmentCommand.useEnvironment(environmentName);
    }

    public void showEnvironment(String environmentName) {
        showEnvironmentCommand.showEnvironment(environmentName);
    }
}
