package com.skerdy.httpulse.terminal.writer.model;

import com.skerdy.httpulse.core.HttpMethod;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class PrintableSendRequest {

    private HttpMethod httpMethod;

    private String url;

    private String body;

    private Map<String, String> headers;

}
