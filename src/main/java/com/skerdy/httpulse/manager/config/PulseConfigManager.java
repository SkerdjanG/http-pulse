package com.skerdy.httpulse.manager.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.skerdy.httpulse.utils.ResourcesUtils;
import org.springframework.stereotype.Component;

import java.io.*;

import static com.skerdy.httpulse.utils.FileUtils.writeFile;

@Component
public class PulseConfigManager {

    private static final String ROOT_CONFIG_DIRECTORY = System.getProperty("user.home")
            + File.separator + ".pulse";

    private static final String PULSE_CONFIG_JSON = ROOT_CONFIG_DIRECTORY + File.separator + "config.json";

    private static final String SAMPLE_CONFIG_DIRECTORY = ROOT_CONFIG_DIRECTORY + File.separator + "sample";

    private static final String SAMPLE_OPEN_API_JSON = SAMPLE_CONFIG_DIRECTORY + File.separator + "open-api.json";

    private static final String SAMPLE_PULSE_REQUESTS = SAMPLE_CONFIG_DIRECTORY + File.separator + "requests.pulse";

    private PulseConfiguration pulseConfiguration;

    private final Gson gson;

    public PulseConfigManager(Gson gson) {
        this.gson = gson;
        ensureRootConfigDirectoryExists();
        retrieveConfigOrSetIfNotExists();
    }

    public PulseConfiguration getPulseConfiguration() {
        return this.pulseConfiguration;
    }

    public void setNewConfig(String activeDirectory, String openApiLocation) {
        this.pulseConfiguration.setActiveDirectory(activeDirectory);
        if (openApiLocation != null && !openApiLocation.isEmpty()) {
            this.pulseConfiguration.setOpenApiSource(openApiLocation);
        }
        var pulseConfigurationFile = new File(PULSE_CONFIG_JSON);
        writeConfigFile(pulseConfigurationFile);
    }

    private void ensureRootConfigDirectoryExists() {
        var pulseConfigDirectory = new File(ROOT_CONFIG_DIRECTORY);
        if (!pulseConfigDirectory.exists()) {
            var success = pulseConfigDirectory.mkdirs();
            if (!success) {
                throw new PulseConfigCreationException();
            }
        }
    }

    private void retrieveConfigOrSetIfNotExists() {
        var pulseConfigurationFile = new File(PULSE_CONFIG_JSON);
        if (!pulseConfigurationFile.exists()) {
            initWithSampleConfig(pulseConfigurationFile);
        }
        recreateSampleDirectory();
        retrieveConfig(pulseConfigurationFile);
    }

    private void retrieveConfig(File pulseConfigurationFile) {
        try {
            JsonReader jsonReader = new JsonReader(new FileReader(pulseConfigurationFile));
            pulseConfiguration = gson.fromJson(jsonReader, PulseConfiguration.class);
            validateConfig();
        } catch (FileNotFoundException e) {
            throw new PulseConfigFileNotReadableException();
        }
    }

    private void initWithSampleConfig(File pulseConfigurationFile) {
        pulseConfiguration = new PulseConfiguration();
        pulseConfiguration.setActiveDirectory(SAMPLE_CONFIG_DIRECTORY);
        pulseConfiguration.setOpenApiSource(SAMPLE_OPEN_API_JSON);
        writeConfigFile(pulseConfigurationFile);
    }

    private void writeConfigFile(File pulseConfigurationFile) {
        var configJsonString = gson.toJson(pulseConfiguration);
        try {
            // write pulse config with sample active directory
            writeFile(pulseConfigurationFile, configJsonString);
        }
        catch (IOException e) {
            throw new PulseConfigFileCreationException();
        }
    }

    private void recreateSampleDirectory() {
        var sampleDirectory = new File(SAMPLE_CONFIG_DIRECTORY);

        // create sample directory with sample openApi json and sample requests.pulse
        sampleDirectory.mkdirs();

        var sampleOpenApiFile = new File(SAMPLE_OPEN_API_JSON);
        var sampleOpenApiJsonText = ResourcesUtils.getTextFromResources("sample-open-api.json");
        try {
            writeFile(sampleOpenApiFile, sampleOpenApiJsonText);
            var samplePulseRequestsFile = new File(SAMPLE_PULSE_REQUESTS);
            var samplePulseRequestsText = ResourcesUtils.getTextFromResources("requests.pulse");
            writeFile(samplePulseRequestsFile, samplePulseRequestsText);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateConfig() {
        if (pulseConfiguration.getActiveDirectory() == null || pulseConfiguration.getActiveDirectory().isEmpty()) {
            throw new InvalidPulseConfigurationException("Active directory is not set. Pulse needs an active directory to work!");
        }
    }
}
