package com.skerdy.httpulse.manager.config;

public interface ConfigManager {

    void setNewConfig(String activeDirectory, String openApiLocation);

    PulseConfiguration getPulseConfiguration();

}
