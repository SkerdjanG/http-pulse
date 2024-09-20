package com.skerdy.httpulse.language;

import com.skerdy.httpulse.language.model.RawPulseRequest;
import com.skerdy.httpulse.language.parser.exception.PulseParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GetRequestParserTest extends LanguageParserBaseTest {

    private static final String BASE_SINGLE_REQUESTS_PATH = "language/single/get/";
    private static final String SIMPLE_GET_REQUEST_PATH = BASE_SINGLE_REQUESTS_PATH + "simple-get.pulse";
    private static final String MULTIPLE_GET_REQUESTS_PATH = "language/multiple/get-requests.pulse";

    @Test
    void shouldParseSimpleGetRequest() {
        // given
        var expectedRequest = RawPulseRequest.builder()
                .name("[Simple GET Request]")
                .httpMethod("GET")
                .url("http://localhost:8080/api/get/simple")
                .headers(Map.of("Content-Type", "application/json"))
                .body(EMPTY_BODY)
                .build();

        // when
        var simpleGetRequest = getFirstRequest(SIMPLE_GET_REQUEST_PATH);

        // then
        assertThat(simpleGetRequest).isEqualTo(expectedRequest);
    }

    @Test
    void shouldParseMultipleGetRequests() {
        // given
        var expectedRequests = List.of(
                RawPulseRequest.builder()
                        .name("[Simple GET Request]")
                        .httpMethod("GET")
                        .url("http://localhost:8080/api/get/simple")
                        .headers(Map.of("Content-Type", "application/json"))
                        .body(EMPTY_BODY)
                        .build(),
                RawPulseRequest.builder()
                        .name("[Simple GET Request Two]")
                        .httpMethod("GET")
                        .url("http://localhost:8080/api/get/simple/1")
                        .headers(Map.of("Content-Type", "application/json"))
                        .body(EMPTY_BODY)
                        .build()
        );

        // when
        var multipleRequests = getAllRequests(MULTIPLE_GET_REQUESTS_PATH);

        // then
        assertThat(multipleRequests).hasSameElementsAs(expectedRequests);
    }

    @ParameterizedTest
    @MethodSource("malformedGetRequests")
    void shouldNotParseMalformedRequests(String malformedRequestPath) {
        // when
        Executable parseExecutable = () -> getAllRequests(malformedRequestPath);

        // then
        assertThrows(PulseParseException.class, parseExecutable);
    }

    private static Stream<Arguments> malformedGetRequests() {
        return IntStream.range(0, 5)
                .mapToObj(index -> Arguments.of(
                        String.format(BASE_SINGLE_REQUESTS_PATH + "malformed/" + "get-%d.pulse", index)
                ));
    }

}
