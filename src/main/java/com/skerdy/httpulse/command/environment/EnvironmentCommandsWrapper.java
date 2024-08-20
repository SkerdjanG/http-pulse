package com.skerdy.httpulse.command.environment;

import org.springframework.stereotype.Component;

@Component
public class EnvironmentCommandsWrapper {

    private final NewEnvironmentCommand newEnvironmentCommand;
    private final ListEnvironmentsCommand listEnvironmentsCommand;
    private final ActiveEnvironmentCommand activeEnvironmentCommand;

    public EnvironmentCommandsWrapper(NewEnvironmentCommand newEnvironmentCommand,
                                      ListEnvironmentsCommand listEnvironmentsCommand,
                                      ActiveEnvironmentCommand activeEnvironmentCommand) {
        this.newEnvironmentCommand = newEnvironmentCommand;
        this.listEnvironmentsCommand = listEnvironmentsCommand;
        this.activeEnvironmentCommand = activeEnvironmentCommand;
    }

    public void newEnvironment(String environmentName) {
        newEnvironmentCommand.newEnvironment(environmentName);
    }

    public void listEnvironments() {
        listEnvironmentsCommand.listEnvironments();
    }

    public void useEnvironment(String environmentName) {
        activeEnvironmentCommand.useEnvironment(environmentName);
    }
}
