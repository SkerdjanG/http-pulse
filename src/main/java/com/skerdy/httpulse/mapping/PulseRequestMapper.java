package com.skerdy.httpulse.mapping;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.skerdy.httpulse.core.HttpMethod;
import com.skerdy.httpulse.core.PulseRequest;
import com.skerdy.httpulse.language.model.RawPulseRequest;
import com.skerdy.httpulse.manager.environment.EnvironmentManager;
import com.skerdy.httpulse.manager.environment.NoActiveEnvironmentException;
import com.skerdy.httpulse.manager.environment.NoVariableDefinedException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PulseRequestMapper {

    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\{#\\w+}");

    private final EnvironmentManager environmentManager;

    public PulseRequestMapper(EnvironmentManager environmentManager) {
        this.environmentManager = environmentManager;
    }

    public PulseRequest fromRawPulseRequest(RawPulseRequest rawPulseRequest) {
        var result = new PulseRequest();
        result.setName(sanitizeName(rawPulseRequest.getName()));
        result.setHttpMethod(HttpMethod.valueOf(rawPulseRequest.getHttpMethod()));
        result.setUrl(processUrl(rawPulseRequest.getUrl()));
        result.setHeaders(rawPulseRequest.getHeaders());
        if (rawPulseRequest.getBody() != null) {
            JsonObject body = JsonParser.parseString(rawPulseRequest.getBody())
                    .getAsJsonObject();
            result.setBody(body.toString());
        }
        return result;
    }

    private String sanitizeName(String rawName) {
        if (rawName != null) {
            return rawName.replace("[", "")
                    .replace("]", "");
        }
        return "";
    }

    private String processUrl(String rawUrl) {
        String result = String.copyValueOf(rawUrl.toCharArray());
        Matcher variableMatcher = VARIABLE_PATTERN.matcher(rawUrl);
        if (rawUrl.contains("{#")) {
            if (environmentManager.getActiveEnvironment() == null || environmentManager.getActiveEnvironment().isEmpty()) {
                // throw exception as there should be an active environment for variables
                throw new NoActiveEnvironmentException(rawUrl);
            }
            while (variableMatcher.find()) {
                var variablePlaceHolder = variableMatcher.group();
                var variableName = variablePlaceHolder.replace("{#", "").replace("}", "");
                var variableValueFromEnvironment = environmentManager.getVariable(variableName);
                if (variableValueFromEnvironment == null) {
                    // throw exception as there is no way to get the value of this variable
                    throw new NoVariableDefinedException(environmentManager.getActiveEnvironment(), variablePlaceHolder);
                } else {
                   result = result.replace("{#" + variableName + "}", variableValueFromEnvironment.toString());
                }
            }
        }
        return result;
    }
}

