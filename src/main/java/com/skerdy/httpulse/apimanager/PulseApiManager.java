package com.skerdy.httpulse.apimanager;

import com.skerdy.httpulse.core.PulseRequest;
import com.skerdy.httpulse.language.parser.PulseParser;
import com.skerdy.httpulse.mapping.PrintableMapper;
import com.skerdy.httpulse.mapping.PulseRequestMapper;
import com.skerdy.httpulse.terminal.writer.TerminalPrettyWriter;
import com.skerdy.httpulse.terminal.writer.model.PrintableRequestIdentifier;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
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

    public void init() {
       requests = pulseParser.parse(getRawText())
               .stream()
               .map(pulseRequestMapper::fromRawPulseRequest)
               .toList();
       listRequests();
    }

    public PulseRequest getRequest(int index) {
        return requests.get(index);
    }

    private String getRawText() {
        try {
            StringWriter writer = new StringWriter();
            InputStream requestsStream = new ClassPathResource("requests.pulse").getInputStream();
            IOUtils.copy(requestsStream, writer, Charset.defaultCharset());
            String rawText = writer.toString();
            requestsStream.close();
            writer.close();
            return rawText;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void listRequests() {
         var printableIdentifiers = new ArrayList<PrintableRequestIdentifier>();
         for (int i = 0; i < requests.size(); i++) {
             printableIdentifiers.add(printableMapper.printableRequestIdentifier(i, requests.get(i)));
         }
         terminalPrettyWriter.printRequestIdentifiers(printableIdentifiers);
    }

}
