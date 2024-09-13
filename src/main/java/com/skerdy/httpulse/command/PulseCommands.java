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

    @Command(command = "init")
    public void init() {
        initialisationCommandsWrapper.init();
    }

    @Command(command = "init new")
    public void initNew() {
        initialisationCommandsWrapper.initNew();
    }

    @Command
    public void fireRequest(@Option(required = true) Integer index) {
        httpExecutionCommand.executeHttpRequest(index);
    }
}
