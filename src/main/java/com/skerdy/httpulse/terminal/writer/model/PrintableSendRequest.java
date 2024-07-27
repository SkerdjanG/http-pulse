package com.skerdy.httpulse.terminal.writer.model;

import com.skerdy.httpulse.core.HttpMethod;

import java.util.Map;

public class PrintableSendRequest {

    private HttpMethod httpMethod;

    private String url;

    private String body;

    private Map<String, String> headers;

    public PrintableSendRequest() {
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

}
