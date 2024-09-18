package com.skerdy.httpulse.terminal.writer.model;

import com.skerdy.httpulse.core.HttpMethod;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PrintableRequestIdentifier {

    private int index;

    private String name;

    private HttpMethod httpMethod;

    private String url;

}
