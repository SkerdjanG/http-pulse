package com.skerdy.httpulse.terminal.writer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrettyJsonify {

    private final Gson gson;

    public String pretty(String json) {
        JsonObject body = JsonParser.parseString(json)
                .getAsJsonObject();
        return gson.toJson(body);
    }

}
