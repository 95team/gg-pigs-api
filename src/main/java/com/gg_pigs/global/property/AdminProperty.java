package com.gg_pigs.global.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Setter
@Getter
@ConfigurationProperties(prefix = "application.admin")
@Configuration
public class AdminProperty {
    private String email;
    private List<String> emails;
}
