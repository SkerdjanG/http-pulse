package com.skerdy.httpulse.manager.config;

public class PulseConfiguration {

    /*
        Can be either the url for retrieving openAPI specification json
        , or the json file location.
     */
    private String openApiSource;

    /*
        This determines the directory where .pulse files that needs to be created (i.e from openAPI or written manually)
        and afterwards to be interpreted and used by http-pulse
     */
    private String activeDirectory;

    public String getOpenApiSource() {
        return openApiSource;
    }

    public void setOpenApiSource(String openApiSource) {
        this.openApiSource = openApiSource;
    }

    public String getActiveDirectory() {
        return activeDirectory;
    }

    public void setActiveDirectory(String activeDirectory) {
        this.activeDirectory = activeDirectory;
    }
}
