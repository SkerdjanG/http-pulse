package com.skerdy.httpulse.manager.environment;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.skerdy.httpulse.manager.config.PulseConfigManager;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.skerdy.httpulse.utils.FileUtils.writeFile;

@Component
public class EnvironmentManager {

    private static final String ENVIRONMENTS_FILENAME = "envs.json";

    private Map<String, Map<String, Object>> environments;

    private String activeEnvironment;

    private final PulseConfigManager pulseConfigManager;

    private final Gson gson;

    public EnvironmentManager(PulseConfigManager pulseConfigManager, Gson gson) {
        this.pulseConfigManager = pulseConfigManager;
        this.gson = gson;
        loadEnvironmentsFromFileSystem();
    }

    public String getEnvironmentsFileLocation() {
        var pulseConfiguration = pulseConfigManager.getPulseConfiguration();
        return pulseConfiguration.getActiveDirectory() + File.separator + ENVIRONMENTS_FILENAME;
    }

    public Object getVariable(String variableKey) {
        loadEnvironmentsFromFileSystem();
        return environments.get(activeEnvironment).get(variableKey);
    }

    public void createNewEnvironment(String environmentName) {
        loadEnvironmentsFromFileSystem();
        if (!environments.containsKey(environmentName)) {
            environments.put(environmentName, new HashMap<>());
            syncChangesToFileSystem(activeEnvironmentsFile());
        } else {
            throw new EnvironmentAlreadyExistsException(environmentName);
        }
    }

    public Set<String> listEnvironments() {
        loadEnvironmentsFromFileSystem();
        return environments.keySet();
    }

    public void useEnvironment(String environmentName) {
        if (environments == null || environments.isEmpty() || !environments.containsKey(environmentName)) {
            throw new UnknownEnvironmentException(environmentName);
        }
        this.activeEnvironment = environmentName;
    }

    public String showEnvironment(String environmentName) {
        if (environments == null || environments.isEmpty() || !environments.containsKey(environmentName)) {
            throw new UnknownEnvironmentException(environmentName);
        }
        var environment = environments.get(environmentName);
        return gson.toJson(environment);
    }

    public String getActiveEnvironment() {
        return this.activeEnvironment;
    }

    private void loadEnvironmentsFromFileSystem() {
        var environmentsFile = activeEnvironmentsFile();
        if (!environmentsFile.exists()) {
            environments = new HashMap<>();
            environments.put("local", new HashMap<>());
            syncChangesToFileSystem(environmentsFile);
        }
        retrieveEnvironmentsFromFile(environmentsFile);
    }

    private void retrieveEnvironmentsFromFile(File environmentsFile) {
        try {
            JsonReader jsonReader = new JsonReader(new FileReader(environmentsFile));
            environments = gson.fromJson(jsonReader, Map.class);
            var environmentKeys = environments.keySet();
            if (activeEnvironment == null || activeEnvironment.isEmpty() || !environmentKeys.contains(activeEnvironment)) {
                if (!environmentKeys.isEmpty()) {
                    activeEnvironment = environmentKeys.stream().findFirst().get();
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }

    private void syncChangesToFileSystem(File environmentsFile) {
        var environmentsJsonString = gson.toJson(environments);
        try {
            writeFile(environmentsFile, environmentsJsonString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File activeEnvironmentsFile() {
        var pulseConfiguration = pulseConfigManager.getPulseConfiguration();
        return new File(pulseConfiguration.getActiveDirectory() + File.separator + ENVIRONMENTS_FILENAME);
    }
}
