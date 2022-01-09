package com.gg_pigs.global.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

public class KafkaProducerProperty {

    @Setter
    @Getter
    @ConfigurationProperties(prefix = "application.kafka.producer.default")
    @Configuration
    public static class KafkaProducerDefaultProperty {
        private String bootstrapServer;
        private Integer lingerMs;
    }

    @Setter
    @Getter
    @ConfigurationProperties(prefix = "application.kafka.producer.ems")
    @Configuration
    public static class KafkaProducerEmsProperty {
        private String bootstrapServer;
        private String topic;
        private Integer lingerMs;
    }
}
