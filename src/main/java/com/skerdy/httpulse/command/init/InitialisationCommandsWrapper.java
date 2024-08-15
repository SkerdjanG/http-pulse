package com.skerdy.httpulse.command.init;

import org.springframework.stereotype.Component;

@Component
public class InitialisationCommandsWrapper {

    private final PulseInitCommand pulseInitCommand;

    public InitialisationCommandsWrapper(PulseInitCommand pulseInitCommand) {
        this.pulseInitCommand = pulseInitCommand;
    }

    public void init() {
        pulseInitCommand.init();
    }

}
