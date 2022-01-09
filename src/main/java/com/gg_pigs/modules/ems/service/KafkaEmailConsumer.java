package com.gg_pigs.modules.ems.service;

import com.gg_pigs.global.property.AdminProperty;
import com.gg_pigs.global.property.MailProperty;
import com.gg_pigs.global.utility.MailHandler;
import com.gg_pigs.modules.ems.domain.EmailAlarm;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class KafkaEmailConsumer {

    private final JavaMailSender javaMailSender;
    private final AdminProperty adminProperty;
    private final MailProperty mailProperty;

    @KafkaListener(
            topics = "gg-pigs-api-poster-request-noti-event",
            containerFactory = "emsKafkaListenerContainerFactory"
    )
    public void listen(@Payload EmailAlarm emsAlarm) throws MessagingException {
        String from = emsAlarm.getFrom();
        List<String> to = emsAlarm.getTo();
        if(Strings.isNullOrEmpty(from)) {
            from = mailProperty.getFrom();
        }
        if(to.isEmpty()) {
            to = adminProperty.getEmails();
        }

        MailHandler mailHandler = new MailHandler(javaMailSender);
        mailHandler.setMail(from, to, emsAlarm.getSubject(), emsAlarm.getMessage());
        mailHandler.send();
    }
}
