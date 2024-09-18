package com.skerdy.httpulse.command.init;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitialisationCommandsWrapper {

    private final PulseInitCommand pulseInitCommand;
    private final PulseInitNewCommand pulseInitNewCommand;

    public void init() {
        pulseInitCommand.init();
    }

    public void initNew() {
        pulseInitNewCommand.initNew();
    }
}
