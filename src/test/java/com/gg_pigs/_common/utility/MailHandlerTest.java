package com.gg_pigs._common.utility;

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

    @DisplayName("테스트: setFrom")
    @Test
    void Test_setFrom() throws MessagingException {
        // Given
        int fromIndex = 0;
        String from = "pigs95team@gmail.com";

        // When
        mailHandler.setFrom(from);

        // Then
        Assertions.assertThat(mailHandler.getFrom().length).isOne();
        Assertions.assertThat(mailHandler.getFrom()[fromIndex].toString()).isEqualTo(from);
    }

    @DisplayName("테스트: setFrom (with AddressException)")
    @Test
    void Test_setFrom_with_AddressException() {
        // Given
        String wrongFrom = "pigs95team@gmail.com@";

        // When
        AddressException exception = assertThrows(AddressException.class, () -> mailHandler.setFrom(wrongFrom));

        // Then
        Assertions.assertThat(exception.getRef()).isEqualTo(wrongFrom);
    }

    @DisplayName("테스트: setTo")
    @Test
    void Test_setTo() throws MessagingException {
        // Given
        int toIndex = 0;
        String to = "pigs95team@gamil.com";

        // When
        mailHandler.setTo(to);

        // Then
        Assertions.assertThat(mailHandler.getTo().length).isOne();
        Assertions.assertThat(mailHandler.getTo()[toIndex].toString()).isEqualTo(to);
    }

    @DisplayName("테스트: setTo (with AddressException)")
    @Test
    void Test_setTo_with_AddressException() {
        // Given
        String wrongTo = "pigs95team@gamil.com@";

        // When
        AddressException exception = assertThrows(AddressException.class, () -> mailHandler.setTo(wrongTo));

        // Then
        Assertions.assertThat(exception.getRef()).isEqualTo(wrongTo);
    }

    @DisplayName("테스트: setSubject")
    @Test
    void Test_setSubject() throws MessagingException {
        // Given
        String subject = "This is a subject";

        // When
        mailHandler.setSubject(subject);

        // Then
        Assertions.assertThat(mailHandler.getSubject()).isEqualTo(subject);
    }

    @DisplayName("테스트: setText")
    @Test
    void Test_setText() throws MessagingException, IOException {
        // Given
        String content = "This is a content";

        // When
        mailHandler.setContent(content);

        // Then
        Assertions.assertThat(mailHandler.getContent().contains(content)).isTrue();
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
        mailHandler.setMailHandler(from ,to, subject, content);

        // Then
        Assertions.assertThat(mailHandler.getFrom()[index].toString()).isEqualTo(from);
        Assertions.assertThat(mailHandler.getTo()[index].toString()).isEqualTo(to);
        Assertions.assertThat(mailHandler.getSubject()).isEqualTo(subject);
        Assertions.assertThat(mailHandler.getContent().contains(content)).isTrue();
    }
}