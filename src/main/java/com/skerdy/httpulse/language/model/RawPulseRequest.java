package com.skerdy.httpulse.language.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RawPulseRequest {

    private String name;

    private String httpMethod;

    private String url;

    private Map<String, String> headers;

    private String body;

}
