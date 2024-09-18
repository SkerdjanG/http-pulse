package com.skerdy.httpulse.manager.config;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

}
