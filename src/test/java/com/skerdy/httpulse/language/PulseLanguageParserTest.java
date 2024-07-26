package com.skerdy.httpulse.language;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.skerdy.httpulse.language.parser.PulseParser;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PulseLanguageParserTest {

    private PulseParser pulseParser;

    @Test
    void whenRequestSyntaxIsProvided_itParsesCorrectlyToInternalModel() {
        // given
        pulseParser = new PulseParser();
        String rawText = "[Post user details]\n" +
                "POST http://example.com/api/user\n" +
                "Content-Type: application/json\n" +
                "{\n" +
                "  \"name\": \"John Doe\",\n" +
                "  \"age\": 30,\n" +
                "  \"address\": {\n" +
                "    \"street\": \"123 Main St\",\n" +
                "    \"city\": \"Anytown\"\n" +
                "  },\n" +
                "  \"phoneNumbers\": [\n" +
                "    \"123-456-7890\",\n" +
                "    \"987-654-3210\"\n" +
                "  ]\n" +
                "}";

        // when
        var requests = pulseParser.parse(rawText);

        // then

        assertThat(requests).hasSize(1);
        assertThat(requests.get(0).getName()).isEqualTo("[Post user details]");
        assertThat(requests.get(0).getHttpMethod()).isEqualTo("POST");
        assertThat(requests.get(0).getUrl()).isEqualTo("http://example.com/api/user");

        assertThat(requests.get(0).getHeaders()).hasSize(1);
        assertThat(requests.get(0).getHeaders().get("Content-Type")).isEqualTo("application/json");


        assertThat(requests.get(0).getBody()).isNotEmpty();

        JsonObject jsonObject = JsonParser.parseString(requests.get(0).getBody())
                .getAsJsonObject();

        assertThat(jsonObject.get("name").getAsString()).isEqualTo("John Doe");
        assertThat(jsonObject.get("age").getAsInt()).isEqualTo(30);
        assertThat(jsonObject.get("address").getAsJsonObject().get("street").getAsString()).isEqualTo("123 Main St");
        assertThat(jsonObject.get("address").getAsJsonObject().get("city").getAsString()).isEqualTo("Anytown");

        var phoneNumbers = jsonObject.get("phoneNumbers").getAsJsonArray().asList().stream().map(JsonElement::getAsString).toList();

        assertThat(phoneNumbers).containsExactly("123-456-7890", "987-654-3210");
    }

    @Test
    void whenTwoRequestsAreProvided_itParsesCorrectlyToInternalModel() {
        // given
        pulseParser = new PulseParser();
        String rawText = "[Post user details]\n" +
                "POST http://example.com/api/user\n" +
                "Content-Type: application/json\n" +
                "{\n" +
                "  \"name\": \"John Doe\",\n" +
                "  \"age\": 30,\n" +
                "  \"address\": {\n" +
                "    \"street\": \"123 Main St\",\n" +
                "    \"city\": \"Anytown\"\n" +
                "  },\n" +
                "  \"phoneNumbers\": [\n" +
                "    \"123-456-7890\",\n" +
                "    \"987-654-3210\"\n" +
                "  ]\n" +
                "}" +
                "\n" +
                "[Second Request]\n" +
                "POST http://example.com/api/user\n" +
                "Header-Name: HeaderValue\n" +
                "Header-Name1: my/header\n" +
                "{\n" +
                "  \"name\": \"Skerdy\"\n" +
                "}";

        // when
        var requests = pulseParser.parse(rawText);

        // then

        assertThat(requests).hasSize(2);
        assertThat(requests.get(0).getName()).isEqualTo("[Post user details]");
        assertThat(requests.get(0).getHttpMethod()).isEqualTo("POST");
        assertThat(requests.get(0).getUrl()).isEqualTo("http://example.com/api/user");

        assertThat(requests.get(0).getHeaders()).hasSize(1);
        assertThat(requests.get(0).getHeaders().get("Content-Type")).isEqualTo("application/json");


        assertThat(requests.get(0).getBody()).isNotEmpty();

        JsonObject jsonObject = JsonParser.parseString(requests.get(0).getBody())
                .getAsJsonObject();

        assertThat(jsonObject.get("name").getAsString()).isEqualTo("John Doe");
        assertThat(jsonObject.get("age").getAsInt()).isEqualTo(30);
        assertThat(jsonObject.get("address").getAsJsonObject().get("street").getAsString()).isEqualTo("123 Main St");
        assertThat(jsonObject.get("address").getAsJsonObject().get("city").getAsString()).isEqualTo("Anytown");

        var phoneNumbers = jsonObject.get("phoneNumbers").getAsJsonArray().asList().stream().map(JsonElement::getAsString).toList();

        assertThat(phoneNumbers).containsExactly("123-456-7890", "987-654-3210");

        assertThat(requests.get(1).getName()).isEqualTo("[Second Request]");
    }


}
