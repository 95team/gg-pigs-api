package com.pangoapi._config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/**")
                .allowedOrigins(
                        "http://localhost:3000",
                        "http://hj2server.ddns.net:8383")
                .allowedMethods("*")
                .allowCredentials(true);
    }
}
