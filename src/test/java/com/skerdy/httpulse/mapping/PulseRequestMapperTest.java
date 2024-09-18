package com.skerdy.httpulse.mapping;

import com.skerdy.httpulse.core.HttpMethod;
import com.skerdy.httpulse.language.model.RawPulseRequest;
import com.skerdy.httpulse.manager.environment.PulseEnvironmentManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PulseRequestMapperTest {

    @Mock
    private PulseEnvironmentManager pulseEnvironmentManager;

    private final PulseRequestMapper pulseRequestMapper = new PulseRequestMapper(pulseEnvironmentManager);

    @Test
    void shouldMapCorrectlyAValidRawPulseRequest() {
        // given
        var rawPulseRequest = new RawPulseRequest();
        rawPulseRequest.setName("[My Request]");
        rawPulseRequest.setBody("{\"age\": 28, \"name\": \"skerdy\"}");
        rawPulseRequest.setHeaders(Map.of(
                "Header1", "Header1/Value",
                "Header2", "Header2/Value"
        ));
        rawPulseRequest.setUrl("http://localhost:8080/api/books");
        rawPulseRequest.setHttpMethod("POST");

        // when
        var pulseRequest = pulseRequestMapper.fromRawPulseRequest(rawPulseRequest);

        // then
        assertAll(
                () -> assertThat(pulseRequest).isNotNull(),
                () -> assertThat(pulseRequest.getUrl()).isEqualTo("http://localhost:8080/api/books"),
                () -> assertThat(pulseRequest.getBody()).isEqualTo("{\"age\":28,\"name\":\"skerdy\"}"),
                () -> assertThat(pulseRequest.getHeaders()).hasSize(2),
                () -> assertThat(pulseRequest.getHttpMethod()).isEqualTo(HttpMethod.POST)
        );
    }

}
