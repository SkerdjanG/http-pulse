package com.skerdy.httpulse.command.init;

import com.skerdy.httpulse.manager.api.PulseApiManager;
import com.skerdy.httpulse.manager.config.PulseConfigManager;
import org.springframework.shell.component.flow.ComponentFlow;
import org.springframework.stereotype.Component;

@Component
public class PulseInitNewCommand {

    private final ComponentFlow.Builder componentFlowBuilder;

    private final PulseConfigManager pulseConfigManager;
    private final PulseApiManager pulseApiManager;

    public PulseInitNewCommand(ComponentFlow.Builder componentFlowBuilder,
                               PulseConfigManager pulseConfigManager,
                               PulseApiManager pulseApiManager) {
        this.componentFlowBuilder = componentFlowBuilder;
        this.pulseConfigManager = pulseConfigManager;
        this.pulseApiManager = pulseApiManager;
    }

    public void initNew() {
        var pulseConfiguration = pulseConfigManager.getPulseConfiguration();

        // ask user to set new active directory
        ComponentFlow activeDirectoryFlow = componentFlowBuilder.clone().reset()
                .withStringInput("activeDirectory")
                .name("Please provide the directory where .pulse request files reside, or you wish to be created: ")
                .and()
                .withConfirmationInput("openApiUsage")
                .name("Do you want to generate .pulse request automatically from your open api json?")
                .and()
                .build();

        var activeDirectoryResult = activeDirectoryFlow.run();

        String providedActiveDirectory = activeDirectoryResult.getContext().get("activeDirectory", String.class);

        if (activeDirectoryResult.getContext().get("openApiUsage").equals(Boolean.TRUE)) {
            // ask for open-api.json url or location
            ComponentFlow openApiFlow = componentFlowBuilder.clone().reset()
                    .withStringInput("openApiLocation")
                    .name("Please provide your open-api.json url or path: ")
                    .and().build();
            var openApiLocationResult = openApiFlow.run();
            var providedOpenApiLocation = openApiLocationResult.getContext().get("openApiLocation", String.class);
            pulseConfigManager.setNewConfig(providedActiveDirectory, providedOpenApiLocation);
            pulseApiManager.init(pulseConfiguration, true);
        } else {
            pulseConfigManager.setNewConfig(providedActiveDirectory, null);
            pulseApiManager.init(pulseConfiguration, false);
        }
    }
}