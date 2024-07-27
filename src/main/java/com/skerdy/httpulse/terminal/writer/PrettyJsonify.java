package com.skerdy.httpulse.terminal.writer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;

@Component
public class PrettyJsonify {

    private final Gson gson;

    public PrettyJsonify(Gson gson) {
        this.gson = gson;
    }

    public String pretty(String json) {
        JsonObject body = JsonParser.parseString(json)
                .getAsJsonObject();
        return gson.toJson(body);
    }

}
