package com.skerdy.httpulse.terminal.writer.model;

public class PrintableReceiveResponse {

    private String body;

    private int statusCode;

    public PrintableReceiveResponse() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}
