package com.gg_pigs.global.config;

import com.gg_pigs.global.property.AdminProperty;
import com.gg_pigs.global.property.KafkaConsumerProperty;
import com.gg_pigs.global.property.KafkaProducerProperty;
import com.gg_pigs.global.property.MailProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({
        AdminProperty.class,
        MailProperty.class,
        KafkaConsumerProperty.KafkaConsumerDefaultProperty.class,
        KafkaConsumerProperty.KafkaConsumerEmsProperty.class,
        KafkaProducerProperty.KafkaProducerDefaultProperty.class,
        KafkaProducerProperty.KafkaProducerEmsProperty.class,
})
public class PropertyConfigurer {
}
