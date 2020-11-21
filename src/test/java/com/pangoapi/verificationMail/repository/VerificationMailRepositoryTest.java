package com.pangoapi.verificationMail.repository;

import com.pangoapi.verificationMail.entity.VerificationMail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class VerificationMailRepositoryTest {

    @Autowired TestEntityManager entityManager;
    @Autowired VerificationMailRepository verificationMailRepository;

    private String sender = "pigs95team@gmail.com";
    private String receiver = "pigs95team@gmail.com";
    private String subject = "This is a subject.";
    private String content = "This is a content.";
    private String verificationCode = "123456";

    @Test
    public void When_call_countByToEmailAndSentDate_Then_return_count() {
        // Given
        Long numberOfRequests = 5L;
        for(int i = 0; i < numberOfRequests; i++) {
            entityManager.persist(VerificationMail.createVerificationMail(sender, receiver, subject, content, verificationCode));
        }

        // When
        Long numberOfSentMails = verificationMailRepository.countByToEmailAndSentDate(receiver, LocalDate.now());

        // Then
        assertThat(numberOfSentMails).isEqualTo(numberOfRequests);
    }
}