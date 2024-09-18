package com.skerdy.httpulse.manager.api;

import com.skerdy.httpulse.core.PulseRequest;
import com.skerdy.httpulse.manager.config.PulseConfiguration;

import java.util.List;

public interface ApiManager {

    void init(PulseConfiguration pulseConfiguration, boolean shouldGenerateOpenApi);

    List<PulseRequest> getRequests();

    PulseRequest getRequest(int index);

}
