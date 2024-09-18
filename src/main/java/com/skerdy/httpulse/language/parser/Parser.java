package com.skerdy.httpulse.language.parser;

import com.skerdy.httpulse.language.model.RawPulseRequest;

import java.util.List;

public interface Parser {

    List<RawPulseRequest> parse(String text);

}
