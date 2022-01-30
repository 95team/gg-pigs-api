package com.gg_pigs.app.poster.service;

import com.gg_pigs.app.historyLog.entity.HistoryLog;
import com.gg_pigs.app.historyLog.service.HistoryLogService;
import com.gg_pigs.app.poster.entity.Poster;
import com.gg_pigs.app.poster.entity.PosterEmsAlarm;
import com.gg_pigs.app.poster.entity.PosterNewEvent;
import com.gg_pigs.global.property.KafkaProducerProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PosterEventHandler {

    private final KafkaTemplate<String, Object> emsKafkaTemplate;
    private final KafkaProducerProperty.KafkaProducerEmsProperty kafkaProducerEmsProperty;

    private final HistoryLogService historyLogService;

    @EventListener
    public void handle(PosterNewEvent event) {
        Poster poster = event.getPoster();
        PosterEmsAlarm posterEmsAlarm = event.getPosterEmsAlarm();

        emsKafkaTemplate.send(kafkaProducerEmsProperty.getTopic4PrNotiEvent(), posterEmsAlarm);

        String title = String.format("[신규] Poster 등록 요청 : %s", poster.getTitle());
        historyLogService.writeHistoryLog(HistoryLog.HistoryLogType.CREATE, null, title, null, true);
    }
}
