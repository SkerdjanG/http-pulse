package com.skerdy.httpulse.command.init;

import com.skerdy.httpulse.manager.api.PulseApiManager;
import com.skerdy.httpulse.manager.config.PulseConfigManager;
import com.skerdy.httpulse.terminal.writer.TerminalPrettyWriter;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.component.flow.ComponentFlow;
import org.springframework.stereotype.Component;

@Component
public class PulseInitCommand {

    private final ComponentFlow.Builder componentFlowBuilder;

    private final PulseConfigManager pulseConfigManager;
    private final TerminalPrettyWriter terminalPrettyWriter;
    private final PulseApiManager pulseApiManager;

    public PulseInitCommand(ComponentFlow.Builder componentFlowBuilder,
                            PulseConfigManager pulseConfigManager,
                            TerminalPrettyWriter terminalPrettyWriter,
                            PulseApiManager pulseApiManager) {
        this.componentFlowBuilder = componentFlowBuilder;
        this.pulseConfigManager = pulseConfigManager;
        this.terminalPrettyWriter = terminalPrettyWriter;
        this.pulseApiManager = pulseApiManager;
    }

    public void init() {
        var pulseConfiguration = pulseConfigManager.getPulseConfiguration();
        terminalPrettyWriter.print(styleConfigurationMesage(pulseConfiguration.getActiveDirectory(),
                pulseConfiguration.getOpenApiSource()));

        ComponentFlow flow = componentFlowBuilder.clone().reset()
                .withConfirmationInput("continue")
                .name("Do you wish to continue with the directory above?")
                .and()
                .build();
        var result = flow.run();


        if (result.getContext().stream().toList().getFirst().getValue() == null || result.getContext().get("continue").equals(Boolean.TRUE)) {
            // continue with the current configuration
            pulseApiManager.init(pulseConfiguration, false);
        } else {
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

    private String styleConfigurationMesage(String activeDirectory, String openApiFileDirectory) {
        return new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.bold())
                .append("Current active directory: ")
                .style(AttributedStyle.DEFAULT.italic())
                .append(activeDirectory)
                .append(System.lineSeparator())
                .style(AttributedStyle.DEFAULT.bold())
                .append("Current open api file: ")
                .style(AttributedStyle.DEFAULT.italic())
                .append(openApiFileDirectory)
                .toAnsi();
    }
}
