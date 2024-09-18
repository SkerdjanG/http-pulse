package com.skerdy.httpulse.manager.environment;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.skerdy.httpulse.manager.config.ConfigManager;
import com.skerdy.httpulse.manager.environment.exception.EnvironmentAlreadyExistsException;
import com.skerdy.httpulse.manager.environment.exception.UnknownEnvironmentException;
import lombok.Getter;
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
public class PulseEnvironmentManager implements EnvironmentManager {

    private static final String ENVIRONMENTS_FILENAME = "envs.json";

    private Map<String, Map<String, Object>> environments;

    @Getter
    private String activeEnvironment;

    private final ConfigManager configManager;

    private final Gson gson;

    public PulseEnvironmentManager(ConfigManager configManager, Gson gson) {
        this.configManager = configManager;
        this.gson = gson;
        loadEnvironmentsFromFileSystem();
    }

    @Override
    public String getEnvironmentsFileLocation() {
        var pulseConfiguration = configManager.getPulseConfiguration();
        return pulseConfiguration.getActiveDirectory() + File.separator + ENVIRONMENTS_FILENAME;
    }

    @Override
    public Object getVariable(String variableKey) {
        loadEnvironmentsFromFileSystem();
        return environments.get(activeEnvironment).get(variableKey);
    }

    @Override
    public void createNewEnvironment(String environmentName) {
        loadEnvironmentsFromFileSystem();
        if (!environments.containsKey(environmentName)) {
            environments.put(environmentName, new HashMap<>());
            syncChangesToFileSystem(activeEnvironmentsFile());
        } else {
            throw new EnvironmentAlreadyExistsException(environmentName);
        }
    }

    @Override
    public Set<String> listEnvironments() {
        loadEnvironmentsFromFileSystem();
        return environments.keySet();
    }

    @Override
    public void useEnvironment(String environmentName) {
        if (environments == null || environments.isEmpty() || !environments.containsKey(environmentName)) {
            throw new UnknownEnvironmentException(environmentName);
        }
        this.activeEnvironment = environmentName;
    }

    @Override
    public String showEnvironment(String environmentName) {
        if (environments == null || environments.isEmpty() || !environments.containsKey(environmentName)) {
            throw new UnknownEnvironmentException(environmentName);
        }
        var environment = environments.get(environmentName);
        return gson.toJson(environment);
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
        var pulseConfiguration = configManager.getPulseConfiguration();
        return new File(pulseConfiguration.getActiveDirectory() + File.separator + ENVIRONMENTS_FILENAME);
    }
}
