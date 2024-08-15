package com.skerdy.httpulse.command;

import com.skerdy.httpulse.command.init.InitialisationCommandsWrapper;
import com.skerdy.httpulse.manager.api.PulseApiManager;
import com.skerdy.httpulse.core.PulseHttpClient;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

@Command(command = "pulse")
public class Commands {

    private final PulseHttpClient pulseHttpClient;

    private final PulseApiManager pulseApiManager;

    private final InitialisationCommandsWrapper initialisationCommandsWrapper;

    public Commands(PulseHttpClient pulseHttpClient,
                    PulseApiManager pulseApiManager,
                    InitialisationCommandsWrapper initialisationCommandsWrapper) {
        this.pulseHttpClient = pulseHttpClient;
        this.pulseApiManager = pulseApiManager;
        this.initialisationCommandsWrapper = initialisationCommandsWrapper;
    }

    @Command(command = "init")
    public void init() {
        initialisationCommandsWrapper.init();
    }

    @Command
    public void fireRequest(@Option Integer index) {
        var pulseRequest = pulseApiManager.getRequest(index);
        pulseHttpClient.execute(pulseRequest);
    }

}
