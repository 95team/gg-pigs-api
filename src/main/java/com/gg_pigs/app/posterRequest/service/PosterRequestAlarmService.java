package com.gg_pigs.app.posterRequest.service;

import com.gg_pigs.global.property.KafkaProducerProperty;
import com.gg_pigs.modules.ems.domain.EmailAlarm;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PosterRequestAlarmService {

    private final KafkaTemplate<String, Object> emsKafkaTemplate;
    private final KafkaProducerProperty.KafkaProducerEmsProperty kafkaProducerEmsProperty;

    public void send(EmailAlarm emailAlarm) {
        emsKafkaTemplate.send(kafkaProducerEmsProperty.getTopic4PrNotiEvent(), emailAlarm);
    }
}
