package com.skerdy.httpulse.terminal.writer;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrettyJsonify {

    private final Gson gson;

    public String pretty(String json) {
        var parsedJsonElement = JsonParser.parseString(json);
        if (parsedJsonElement.isJsonArray()) {
            var jsonArray = parsedJsonElement.getAsJsonArray();
            return gson.toJson(jsonArray);
        } else if (parsedJsonElement.isJsonObject()) {
            var jsonObject = parsedJsonElement.getAsJsonObject();
            return gson.toJson(jsonObject);
        } else if (parsedJsonElement.isJsonPrimitive()) {
            var jsonPrimitive = parsedJsonElement.getAsJsonPrimitive();
            return gson.toJson(jsonPrimitive);
        } else if (parsedJsonElement.isJsonNull()) {
            return "null";
        }
        return "Could not deserialise response";
    }

}
