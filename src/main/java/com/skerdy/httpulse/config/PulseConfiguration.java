package com.skerdy.httpulse.config;

import com.skerdy.httpulse.language.parser.PulseParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PulseConfiguration {

    @Bean
    public PulseParser pulseParser() {
        return new PulseParser();
    }

}
