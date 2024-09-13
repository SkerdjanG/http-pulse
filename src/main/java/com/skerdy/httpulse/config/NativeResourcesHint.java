package com.skerdy.httpulse.config;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@Configuration
@ImportRuntimeHints(NativeResourcesHint.NativeResourcesRegistrar.class)
public class NativeResourcesHint {

    static class NativeResourcesRegistrar implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.resources()
                    .registerPattern("*.pulse");
        }
    }
}
