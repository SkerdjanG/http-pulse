package com.skerdy.httpulse.core.request;

import com.skerdy.httpulse.core.PulseRequest;

import java.net.http.HttpRequest;

public interface RequestGenerator {

    HttpRequest generate(PulseRequest pulseRequest);

}
