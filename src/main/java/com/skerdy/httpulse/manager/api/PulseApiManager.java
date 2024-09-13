package com.skerdy.httpulse.manager.api;

import com.skerdy.httpulse.core.PulseRequest;
import com.skerdy.httpulse.language.parser.PulseParseException;
import com.skerdy.httpulse.language.parser.PulseParser;
import com.skerdy.httpulse.manager.config.PulseConfiguration;
import com.skerdy.httpulse.manager.environment.NoActiveEnvironmentException;
import com.skerdy.httpulse.manager.environment.NoVariableDefinedException;
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
        try {
            for (String pulseFileName : pulseFileNames) {
                requests.addAll(pulseParser.parse(ResourcesUtils.getRawTextFromPulseFile(activeDirectory + File.separator + pulseFileName))
                        .stream()
                        .map(pulseRequestMapper::fromRawPulseRequest)
                        .toList());
            }
        } catch (PulseParseException pulseParseException) {
            terminalPrettyWriter.print(stylePulseParseException(pulseParseException));
        } catch (NoActiveEnvironmentException noActiveEnvironmentException) {
            terminalPrettyWriter.print(styleNoActiveEnvironmentException(noActiveEnvironmentException));
        } catch (NoVariableDefinedException noVariableDefinedException) {
            terminalPrettyWriter.print(styleNoVariableDefinedException(noVariableDefinedException));
        }
        if (!requests.isEmpty()) {
            listRequests();
        }
    }

    public List<PulseRequest> getRequests() {
        return this.requests;
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

    private String styleNoVariableDefinedException(NoVariableDefinedException noVariableDefinedException) {
        return new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.RED).bold())
                .append("Error during parsing of .pulse file!")
                .append(System.lineSeparator())
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW).bold())
                .append("Details: ")
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW).italic())
                .append(noVariableDefinedException.getMessage())
                .append(System.lineSeparator())
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE).bold())
                .append("Please make sure the variable is properly defined for this environment!")
                .toAnsi();
    }

    private String styleNoActiveEnvironmentException(NoActiveEnvironmentException noActiveEnvironmentException) {
        return new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.RED).bold())
                .append("Error during parsing of .pulse file!")
                .append(System.lineSeparator())
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW).bold())
                .append("Details: ")
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW).italic())
                .append(noActiveEnvironmentException.getMessage())
                .append(System.lineSeparator())
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE).bold())
                .append("Please make sure there is an active environment defined and used!")
                .toAnsi();
    }

    private String stylePulseParseException(PulseParseException pulseParseException) {
        return new AttributedStringBuilder()
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.RED).bold())
                .append("Error during parsing of .pulse file!")
                .append(System.lineSeparator())
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW).bold())
                .append("Details: ")
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW).italic())
                .append(pulseParseException.getMessage())
                .append(System.lineSeparator())
                .style(AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE).bold())
                .append("Please try to fix the problem with your .pulse file and then try again.")
                .toAnsi();
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
