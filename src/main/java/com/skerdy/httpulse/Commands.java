package com.skerdy.httpulse;

import com.skerdy.httpulse.core.HttpMethod;
import com.skerdy.httpulse.core.PulseHttpClient;
import com.skerdy.httpulse.core.PulseRequest;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Map;

@ShellComponent
public class Commands {

    private final PulseHttpClient pulseHttpClient;

    public Commands(PulseHttpClient pulseHttpClient) {
        this.pulseHttpClient = pulseHttpClient;
    }

    @ShellMethod(key = "http")
    public String method() {
        // test only
        PulseRequest pulseRequest = new PulseRequest();
        pulseRequest.setUrl("http://localhost:8080/api/post/simple");
        pulseRequest.setHttpMethod(HttpMethod.POST);
        pulseRequest.setHeaders(Map.of("Content-Type","application/json"));
        pulseRequest.setBody("{\"stringValue\": \"RANDOM_STRING\"}");
        return pulseHttpClient.execute(pulseRequest);
    }
}
