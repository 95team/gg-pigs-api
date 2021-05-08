package com.gg_pigs.verificationMail.entity;

import com.gg_pigs._common.enums.VerificationMailStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

class VerificationMailTest {

    VerificationMail verificationMail = new VerificationMail();

    Long id = 1L;
    String toEmail = "pigs95team@gmail.com";
    String fromEmail = "pigs95team@gmail.com";
    String verificationCoude = "123456";
    String status = VerificationMailStatus.SUCCESS.FAILURE.name();
    String subject = "This is a subject";
    String content = "This is a content";
    LocalDate sentDate = LocalDate.now();

    @DisplayName("[테스트] AllArgsConstructor()")
    @Test
    void Test_AllArgsConstructor() {
        // Given // When
        VerificationMail verificationMail = new VerificationMail(id, toEmail, fromEmail, verificationCoude, status, subject, content, sentDate);

        // Then
        Assertions.assertThat(verificationMail.getId()).isEqualTo(id);
    }

    @DisplayName("[테스트] changeStatusToSuccess()")
    @Test
    void Test_changeStatusToSuccess() {
        // Given
        String expectedStatus = VerificationMailStatus.SUCCESS.name();

        // When
        verificationMail.changeStatusToSuccess();

        // Then
        Assertions.assertThat(verificationMail.getStatus()).isEqualTo(expectedStatus);
    }

    @DisplayName("[테스트] changeStatusToFailure()")
    @Test
    void Test_changeStatusToFailure() {
        // Given
        String expectedStatus = VerificationMailStatus.FAILURE.name();

        // When
        verificationMail.changeStatusToFailure();

        // Then
        Assertions.assertThat(verificationMail.getStatus()).isEqualTo(expectedStatus);
    }

    @DisplayName("[테스트] checkEmailFormat() : 정상적인 이메일")
    @Test
    void Test_checkEmailFormat() {
        // Given
        String email = "pigs95team@gmail.com";

        // When
        Boolean checkResult = VerificationMail.checkEmailFormat(email);

        // Then
        Assertions.assertThat(checkResult).isTrue();
    }

    @DisplayName("[테스트] checkEmailFormat() : 비정상적인 이메일 (case1)")
    @Test
    void Test_checkEmailFormat_with_wrong_email_case1() {
        // Given
        String email = "pigs95team@gmail.com@";

        // When
        Boolean checkResult = VerificationMail.checkEmailFormat(email);

        // Then
        Assertions.assertThat(checkResult).isFalse();
    }

    @DisplayName("[테스트] checkEmailFormat() : 비정상적인 이메일 (case2)")
    @Test
    void Test_checkEmailFormat_with_wrong_email_case2() {
        // Given
        String email = "pigs95team@@gmail.com";

        // When
        Boolean checkResult = VerificationMail.checkEmailFormat(email);

        // Then
        Assertions.assertThat(checkResult).isFalse();
    }

    @DisplayName("[테스트] makeSubject()")
    @Test
    void Test_makeSubject() {
        // Given
        String expectedSubject = "인증코드를 입력해주세요!";

        // When
        String subject = VerificationMail.makeSubject();

        // Then
        Assertions.assertThat(subject).isEqualTo(expectedSubject);
    }

    @DisplayName("[테스트] makeContent()")
    @Test
    void Test_makeContent() throws IOException {
        // Given
        String verificationCode = "156432";

        // When
        String content = VerificationMail.makeContent(verificationCode);

        // Then
        Assertions.assertThat(content.contains(verificationCode)).isTrue();
    }

    @DisplayName("[테스트] makeVerificationCode()")
    @Test
    void Test_makeVerificationCode() {
        // Given
        int expectedLength = 6;

        // When
        String verificationCode = VerificationMail.makeVerificationCode();

        // Then
        Assertions.assertThat(verificationCode.length()).isEqualTo(expectedLength);
    }

    @DisplayName("[테스트] createVerificationMail()")
    @Test
    void Test_createVerificationMail() {
        // Given // When
        VerificationMail verificationMail = VerificationMail.createVerificationMail(fromEmail, toEmail, subject, content, verificationCoude);

        // Then
        Assertions.assertThat(verificationMail.getId()).isNull();
        Assertions.assertThat(verificationMail.getStatus()).isEqualTo(VerificationMailStatus.WAITING.name());
    }
}