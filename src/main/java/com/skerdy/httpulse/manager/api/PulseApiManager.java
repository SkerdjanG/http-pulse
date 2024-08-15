package com.skerdy.httpulse.manager.api;

import com.skerdy.httpulse.core.PulseRequest;
import com.skerdy.httpulse.language.parser.PulseParser;
import com.skerdy.httpulse.manager.config.PulseConfiguration;
import com.skerdy.httpulse.mapping.PrintableMapper;
import com.skerdy.httpulse.mapping.PulseRequestMapper;
import com.skerdy.httpulse.terminal.writer.TerminalPrettyWriter;
import com.skerdy.httpulse.terminal.writer.model.PrintableRequestIdentifier;
import com.skerdy.httpulse.utils.ResourcesUtils;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class PulseApiManager {

    private final PulseParser pulseParser;

    private final PulseRequestMapper pulseRequestMapper;

    private final PrintableMapper printableMapper;

    private final TerminalPrettyWriter terminalPrettyWriter;

    private List<PulseRequest> requests;

    public PulseApiManager(PulseParser pulseParser,
                           PulseRequestMapper pulseRequestMapper,
                           PrintableMapper printableMapper,
                           TerminalPrettyWriter terminalPrettyWriter) {
        this.pulseParser = pulseParser;
        this.pulseRequestMapper = pulseRequestMapper;
        this.printableMapper = printableMapper;
        this.terminalPrettyWriter = terminalPrettyWriter;
    }

    public void init(PulseConfiguration pulseConfiguration, boolean shouldGenerateOpenApi) {
        requests = new ArrayList<>();
        var activeDirectory = pulseConfiguration.getActiveDirectory();
        var pulseFileNames = ResourcesUtils.getPulseRequestFilesInActiveDirectory(activeDirectory);
        terminalPrettyWriter.print(styleDiscoveredRequestFiles(pulseFileNames));
        for (String pulseFileName : pulseFileNames) {
            requests.addAll(pulseParser.parse(ResourcesUtils.getRawTextFromPulseFile(activeDirectory + File.separator + pulseFileName))
               .stream()
               .map(pulseRequestMapper::fromRawPulseRequest)
               .toList());
        }
        if (!requests.isEmpty()) {
            listRequests();
        }
    }

    public PulseRequest getRequest(int index) {
        return requests.get(index);
    }

    private void listRequests() {
         var printableIdentifiers = new ArrayList<PrintableRequestIdentifier>();
         for (int i = 0; i < requests.size(); i++) {
             printableIdentifiers.add(printableMapper.printableRequestIdentifier(i, requests.get(i)));
         }
         terminalPrettyWriter.printRequestIdentifiers(printableIdentifiers);
    }

    private String styleDiscoveredRequestFiles(List<String> requestFiles) {
        if (requestFiles.isEmpty()) {
            return new AttributedStringBuilder()
                    .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.RED).bold())
                    .append("There are no .pulse files in this directory!")
                    .append(System.lineSeparator())
                    .append("Consider adding .pulse files yourself or automatically generate from open api specification.")
                    .toAnsi();
        }
        return new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE).bold())
                .append("Found the following .pulse files: ")
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW).italic())
                .append(String.join(", ", requestFiles))
                .toAnsi();
    }
}
