package com.skerdy.httpulse.apimanager;

import com.skerdy.httpulse.core.PulseRequest;
import com.skerdy.httpulse.language.parser.PulseParser;
import com.skerdy.httpulse.mapping.PulseRequestMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.List;

@Component
public class PulseApiManager {

    private final PulseParser pulseParser;

    private final PulseRequestMapper pulseRequestMapper;

    private List<PulseRequest> requests;

    public PulseApiManager(PulseParser pulseParser, PulseRequestMapper pulseRequestMapper) {
        this.pulseParser = pulseParser;
        this.pulseRequestMapper = pulseRequestMapper;
    }

    public String init() {
       requests = pulseParser.parse(getRawText())
               .stream()
               .map(pulseRequestMapper::fromRawPulseRequest)
               .toList();
       return listRequests();
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

    private String listRequests() {
        var result = new StringBuilder();
        for(int i = 0; i < requests.size(); i++) {
            result.append(i).append(" - ").append(requests.get(i).getUrl()).append(System.lineSeparator());
        }
        return result.toString();
    }
}
