package com.skerdy.httpulse.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skerdy.httpulse.Commands;
import com.skerdy.httpulse.language.parser.PulseParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.command.annotation.EnableCommand;

@Configuration
@EnableCommand(Commands.class)
public class PulseConfiguration {

    @Bean
    public PulseParser pulseParser() {
        return new PulseParser();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

}
