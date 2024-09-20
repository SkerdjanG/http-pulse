package com.skerdy.httpulse.language;

import com.skerdy.httpulse.language.model.RawPulseRequest;
import com.skerdy.httpulse.language.parser.Parser;
import com.skerdy.httpulse.language.parser.PulseParser;
import com.skerdy.httpulse.utils.ResourcesUtils;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

public class LanguageParserBaseTest {

    protected static final String EMPTY_BODY = null;

    protected Parser parser;

    @BeforeEach
    protected void setupParser() {
        parser = new PulseParser();
    }

    protected RawPulseRequest getFirstRequest(String resourcePath) {
        return parser.parse(ResourcesUtils.getTextFromResources(resourcePath)).getFirst();
    }

    protected List<RawPulseRequest> getAllRequests(String resourcePath) {
        return parser.parse(ResourcesUtils.getTextFromResources(resourcePath));
    }

}