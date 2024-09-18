package com.skerdy.httpulse.manager.environment;

import java.util.Set;

public interface EnvironmentManager {

    String getEnvironmentsFileLocation();

    String getActiveEnvironment();

    Object getVariable(String variableKey);

    void createNewEnvironment(String environmentName);

    Set<String> listEnvironments();

    void useEnvironment(String environmentName);

    String showEnvironment(String environmentName);
}
