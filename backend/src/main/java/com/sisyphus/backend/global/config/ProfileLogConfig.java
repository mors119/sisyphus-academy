package com.sisyphus.backend.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.core.env.Environment;

@RequiredArgsConstructor
@Configuration
public class ProfileLogConfig {
    private final Environment env;

    @EventListener(ApplicationReadyEvent.class)
    public void logProfiles() {
        System.out.println("ACTIVE PROFILES: " + String.join(",", env.getActiveProfiles()));
    }
}