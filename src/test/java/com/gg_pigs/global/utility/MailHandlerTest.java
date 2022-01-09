package com.gg_pigs.global.utility;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(
        classes = {
                JavaMailSenderImpl.class
        }
)
class MailHandlerTest {

    @Autowired JavaMailSender javaMailSender;

    MailHandler mailHandler;

    @BeforeEach
    void setUp() throws MessagingException {
        mailHandler = new MailHandler(javaMailSender);
    }

    @DisplayName("테스트: setMailHandler")
    @Test
    void Test_setMailHandler() throws MessagingException, IOException {
        // Given
        int index = 0;
        String from = "pigs95team@gamil.com";
        String to = "pigs95team@gamil.com";
        String subject = "This is a subject";
        String content = "This is a subject";

        // When
        mailHandler.setMail(from ,to, subject, content);

        // Then
        Assertions.assertThat(mailHandler.getFrom()[index].toString()).isEqualTo(from);
        Assertions.assertThat(mailHandler.getTo()[index].toString()).isEqualTo(to);
        Assertions.assertThat(mailHandler.getSubject()).isEqualTo(subject);
        Assertions.assertThat(mailHandler.getContent().contains(content)).isTrue();
    }
}