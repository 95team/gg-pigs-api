package com.gg_pigs.global.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

public class KafkaConsumerProperty {

    @Setter
    @Getter
    @ConfigurationProperties(prefix = "application.kafka.consumer.default")
    @Configuration
    public static class KafkaConsumerDefaultProperty {
        private String bootstrapServer;
        private String groupId;
        private String autoOffsetReset;
    }

    @Setter
    @Getter
    @ConfigurationProperties(prefix = "application.kafka.consumer.ems")
    @Configuration
    public static class KafkaConsumerEmsProperty {
        private String bootstrapServer;
        private String topic;
        private String groupId;
        private String autoOffsetReset;
    }
}
