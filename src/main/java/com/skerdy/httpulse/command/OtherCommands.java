package com.skerdy.httpulse.command;

import com.skerdy.httpulse.command.environment.EnvironmentCommandsWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

@Command
@RequiredArgsConstructor
public class OtherCommands {

    private final EnvironmentCommandsWrapper environmentCommandsWrapper;

    @Command(command = "new env",
            description = "Creates a new empty environment in the working directory.")
    public void newEnvironment(@Option(required = true, description = "The name of the new environment to be created.") String environmentName) {
        environmentCommandsWrapper.newEnvironment(environmentName);
    }

    @Command(command = "list env", description = "Lists all the environments in the working directory.")
    public void listEnvironments() {
        environmentCommandsWrapper.listEnvironments();
    }

    @Command(command = "use env", description = "Sets the specified environment passed as argument to the current active one.")
    public void useEnvironment(@Option(required = true, description = "The name of the environment to be set as active.") String environmentName) {
        environmentCommandsWrapper.useEnvironment(environmentName);
    }

    @Command(command = "show env", description = "Prints the content of the specified environment passed as an argument.")
    public void showEnvironment(@Option(required = true, description = "The name of the environment to be shown.") String environmentName) {
        environmentCommandsWrapper.showEnvironment(environmentName);
    }

}
