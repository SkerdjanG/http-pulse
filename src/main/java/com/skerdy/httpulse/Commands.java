package com.skerdy.httpulse;

import com.skerdy.httpulse.apimanager.PulseApiManager;
import com.skerdy.httpulse.core.PulseHttpClient;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

@Command(command = "pulse")
public class Commands {

    private final PulseHttpClient pulseHttpClient;

    private final PulseApiManager pulseApiManager;

    public Commands(PulseHttpClient pulseHttpClient, PulseApiManager pulseApiManager) {
        this.pulseHttpClient = pulseHttpClient;
        this.pulseApiManager = pulseApiManager;
    }

    @Command(command = "init")
    public void init() {
        pulseApiManager.init();
    }

    @Command
    public void fireRequest(@Option Integer index) {
        var pulseRequest = pulseApiManager.getRequest(index);
        pulseHttpClient.execute(pulseRequest);
    }

}
