package com.skerdy.httpulse.command;

import com.skerdy.httpulse.command.execution.HttpExecutionCommand;
import com.skerdy.httpulse.command.init.InitialisationCommandsWrapper;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

@Command(command = "pulse")
public class PulseCommands {

    private final InitialisationCommandsWrapper initialisationCommandsWrapper;
    private final HttpExecutionCommand httpExecutionCommand;

    public PulseCommands(InitialisationCommandsWrapper initialisationCommandsWrapper,
                         HttpExecutionCommand httpExecutionCommand) {
        this.initialisationCommandsWrapper = initialisationCommandsWrapper;
        this.httpExecutionCommand = httpExecutionCommand;
    }

    @Command(command = "init", description = "Initializes http-pulse in the last configured workspace.")
    public void init() {
        initialisationCommandsWrapper.init();
    }

    @Command(command = "init new", description = "Initializes http-pulse in a new workspace.")
    public void initNew() {
        initialisationCommandsWrapper.initNew();
    }

    @Command(description = "Executes the desired request by passing it's index as an argument.")
    public void fireRequest(@Option(required = true,
            description = "The index of the request which needs to be executed.") Integer index) {
        httpExecutionCommand.executeHttpRequest(index);
    }
}
