package com.skerdy.httpulse.terminal.writer.model;

import com.skerdy.httpulse.core.HttpMethod;

public class PrintableRequestIdentifier {

    private int index;

    private String name;

    private HttpMethod httpMethod;

    private String url;

    public PrintableRequestIdentifier() {
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
