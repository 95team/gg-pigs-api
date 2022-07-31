package com.gg_pigs.global.config;

import com.gg_pigs.global.property.KafkaConsumerProperty;
import com.gg_pigs.global.property.KafkaProducerProperty;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;

public class KafkaConfigurer {

    @ConditionalOnProperty(name = "application.kafka.producer.enable", havingValue = "true")
    @RequiredArgsConstructor
    @Configuration
    @EnableKafka
    public static class Producer {
        private final KafkaProducerProperty.KafkaProducerDefaultProperty kafkaProducerDefaultProperty;
        private final KafkaProducerProperty.KafkaProducerEmsProperty kafkaProducerEmsProperty;

        @Bean
        public KafkaTemplate<String, Object> defaultKafkaTemplate(ProducerFactory<String, Object> defaultProducerFactory) {
            return new KafkaTemplate<>(defaultProducerFactory);
        }

        @Bean
        public KafkaTemplate<String, Object> emsKafkaTemplate(ProducerFactory<String, Object> emsProducerFactory) {
            return new KafkaTemplate<>(emsProducerFactory);
        }

        @Bean
        ProducerFactory<String, Object> defaultProducerFactory() {
            return new DefaultKafkaProducerFactory<>(
                    new HashMap<String, Object>(){{
                        put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProducerDefaultProperty.getBootstrapServer());
                        put(ProducerConfig.LINGER_MS_CONFIG, kafkaProducerDefaultProperty.getLingerMs());
                        put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
                        put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
                    }}
            );
        }

        @Bean
        ProducerFactory<String, Object> emsProducerFactory() {
            return new DefaultKafkaProducerFactory<>(
                    new HashMap<String, Object>(){{
                        put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProducerEmsProperty.getBootstrapServer());
                        put(ProducerConfig.LINGER_MS_CONFIG, kafkaProducerEmsProperty.getLingerMs());
                        put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
                        put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
                    }}
            );
        }
    }

    @ConditionalOnProperty(name = "application.kafka.consumer.enable", havingValue = "true")
    @RequiredArgsConstructor
    @Configuration
    @EnableKafka
    public static class Consumer {
        private final KafkaConsumerProperty.KafkaConsumerDefaultProperty kafkaConsumerDefaultProperty;
        private final KafkaConsumerProperty.KafkaConsumerEmsProperty kafkaConsumerEmsProperty;

        @Bean
        ConcurrentKafkaListenerContainerFactory<String, Object> defaultKafkaListenerContainerFactory(ConsumerFactory<String, Object> defaultConsumerFactory) {
            ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(defaultConsumerFactory);

            return factory;
        }

        @Bean
        ConcurrentKafkaListenerContainerFactory<String, Object> emsKafkaListenerContainerFactory(ConsumerFactory<String, Object> emsConsumerFactory) {
            ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(emsConsumerFactory);

            return factory;
        }

        @Bean
        public ConsumerFactory<String, Object> defaultConsumerFactory() {
            JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
            jsonDeserializer.addTrustedPackages("*");

            return new DefaultKafkaConsumerFactory<>(
                    new HashMap<String, Object>() {{
                        put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConsumerDefaultProperty.getBootstrapServer());
                        put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerDefaultProperty.getGroupId());
                        put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConsumerDefaultProperty.getAutoOffsetReset());
                        put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
                        put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
                    }},
                    new StringDeserializer(),
                    jsonDeserializer
            );
        }

        @Bean
        public ConsumerFactory<String, Object> emsConsumerFactory() {
            JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
            jsonDeserializer.addTrustedPackages("*");

            return new DefaultKafkaConsumerFactory<>(
                    new HashMap<String, Object>(){{
                        put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConsumerEmsProperty.getBootstrapServer());
                        put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerEmsProperty.getGroupId());
                        put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaConsumerEmsProperty.getAutoOffsetReset());
                        put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
                        put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
                    }},
                    new StringDeserializer(),
                    jsonDeserializer
            );
        }
    }
}
