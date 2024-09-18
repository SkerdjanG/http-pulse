package com.skerdy.httpulse.terminal.writer.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PrintableReceiveResponse {

    private String body;

    private int statusCode;

}
