package com.skerdy.httpulse;

import com.skerdy.httpulse.apimanager.PulseApiManager;
import com.skerdy.httpulse.core.PulseHttpClient;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class Commands {

    private final PulseHttpClient pulseHttpClient;

    private final PulseApiManager pulseApiManager;

    public Commands(PulseHttpClient pulseHttpClient, PulseApiManager pulseApiManager) {
        this.pulseHttpClient = pulseHttpClient;
        this.pulseApiManager = pulseApiManager;
    }

    @ShellMethod(key = "pulse init")
    public String init() {
        return pulseApiManager.init();
    }

    @ShellMethod(key = "pulse")
    public String fireRequest(@ShellOption Integer index) {
        var pulseRequest = pulseApiManager.getRequest(index);
        return pulseHttpClient.execute(pulseRequest);
    }
}
