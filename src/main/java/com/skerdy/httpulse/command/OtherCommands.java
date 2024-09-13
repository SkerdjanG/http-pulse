package com.skerdy.httpulse.command;

import com.skerdy.httpulse.command.environment.EnvironmentCommandsWrapper;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

@Command
public class OtherCommands {

    private final EnvironmentCommandsWrapper environmentCommandsWrapper;

    public OtherCommands(EnvironmentCommandsWrapper environmentCommandsWrapper) {
        this.environmentCommandsWrapper = environmentCommandsWrapper;
    }

    @Command(command = "new env")
    public void newEnvironment(@Option(required = true) String environmentName) {
        environmentCommandsWrapper.newEnvironment(environmentName);
    }

    @Command(command = "list env")
    public void listEnvironments() {
        environmentCommandsWrapper.listEnvironments();
    }

    @Command(command = "use env")
    public void useEnvironment(@Option(required = true) String environmentName) {
        environmentCommandsWrapper.useEnvironment(environmentName);
    }

    @Command(command = "show env")
    public void showEnvironment(@Option(required = true) String environmentName) {
        environmentCommandsWrapper.showEnvironment(environmentName);
    }

}
