package com.gg_pigs._common.config;

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
                        "http://localhost:8000",
                        "https://gg-pigs.com"
                )
                .allowedMethods("*")
                .allowCredentials(true);

        registry.addMapping("/api/v2/**")
                .allowedOrigins(
                        "http://localhost:3000",
                        "http://localhost:8000",
                        "https://gg-pigs.com"
                )
                .allowedMethods("*")
                .allowCredentials(true);
    }
}
