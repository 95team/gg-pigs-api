package com.gg_pigs.app.poster.service;

import com.gg_pigs.app.poster.entity.PosterEmsAlarm;
import com.gg_pigs.global.property.KafkaProducerProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PosterAlarmEventHandler {

    private final KafkaTemplate<String, Object> emsKafkaTemplate;
    private final KafkaProducerProperty.KafkaProducerEmsProperty kafkaProducerEmsProperty;

    @EventListener
    public void handle(PosterEmsAlarm emsAlarm) {
        emsKafkaTemplate.send(kafkaProducerEmsProperty.getTopic4PrNotiEvent(), emsAlarm);
    }
}
