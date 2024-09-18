package com.skerdy.httpulse.core;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class PulseRequest {

    private String name;

    private HttpMethod httpMethod;

    private String url;

    private String body;

    private Map<String, String> headers;

}
