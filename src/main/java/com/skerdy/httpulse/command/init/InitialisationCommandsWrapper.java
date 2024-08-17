package com.skerdy.httpulse.command.init;

import org.springframework.stereotype.Component;

@Component
public class InitialisationCommandsWrapper {

    private final PulseInitCommand pulseInitCommand;
    private final PulseInitNewCommand pulseInitNewCommand;

    public InitialisationCommandsWrapper(PulseInitCommand pulseInitCommand, PulseInitNewCommand pulseInitNewCommand) {
        this.pulseInitCommand = pulseInitCommand;
        this.pulseInitNewCommand = pulseInitNewCommand;
    }

    public void init() {
        pulseInitCommand.init();
    }

    public void initNew() {
        pulseInitNewCommand.initNew();
    }
}
