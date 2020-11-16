package com.pangoapi.verificationMail.service;

import com.pangoapi._common.utility.MailHandler;
import com.pangoapi.verificationMail.dto.RequestDtoVerificationMail;
import com.pangoapi.verificationMail.dto.ResponseDtoVerificationMail;
import com.pangoapi.verificationMail.entity.VerificationMail;
import com.pangoapi.verificationMail.repository.VerificationMailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@SpringBootTest(
        classes = {
                VerificationMailService.class
        }
)
class VerificationMailServiceTest {

    @Autowired Environment environment;

    @MockBean MailHandler mailHandler;
    @MockBean JavaMailSender javaMailSender;
    @MockBean VerificationMailRepository verificationMailRepository;

    @Mock VerificationMail sentVerificationMail;
    @Mock RequestDtoVerificationMail requestDtoVerificationMail;
    @Mock ResponseDtoVerificationMail responseDtoVerificationMail;

    private VerificationMailService verificationMailService;
    private String receiver = "pigs95team@gmail.com";

    @BeforeEach
    void setUp() throws Exception {
        verificationMailService = Mockito.spy(new VerificationMailService(environment, verificationMailRepository, javaMailSender));

        // Configuration of verificationMailService
        Mockito.doReturn(mailHandler).when(verificationMailService).makeMailHandler(any(JavaMailSender.class));
        Mockito.doReturn(responseDtoVerificationMail).when(verificationMailService).makeResponseDtoVerificationMail();

        // Configuration of verificationMailRepository
        Mockito.when(verificationMailRepository.save(any(VerificationMail.class))).thenReturn(sentVerificationMail);
        Mockito.when(verificationMailRepository.findById(any(Long.class))).thenReturn(Optional.of(sentVerificationMail));

        // Configuration of requestDtoVerificationMail
        Mockito.when(requestDtoVerificationMail.getReceiver()).thenReturn(receiver);
    }

    @Test
    public void When_call_sendVerificationEmail_Then_return_responseDtoVerificationMail() throws Exception {
        // Given // When
        responseDtoVerificationMail = verificationMailService.sendVerificationEmail(requestDtoVerificationMail);

        // Then
        Mockito.verify(verificationMailRepository, times(1)).save(any(VerificationMail.class));
        Mockito.verify(verificationMailRepository, times(1)).findById(anyLong());
        Mockito.verify(sentVerificationMail, times(1)).changeStatusToSuccess();
        Mockito.verify(responseDtoVerificationMail, times(1)).changeToSuccess(anyString(), anyString(), anyString());
    }

    @Test
    public void When_call_makeVerificationCode_Then_return_6_digit_string() {
        // Given
        int lengthOfVerificationCode = 6;

        // When
        String verificationCode = VerificationMail.makeVerificationCode();

        // Then
        assertThat(verificationCode.length()).isEqualTo(lengthOfVerificationCode);
    }

    @Test
    public void When_call_checkEmailFormat_Then_check_emailFormat() {
        // Given
        String wrongEmailCase1 = "example@@example.com";
        String wrongEmailCase2 = "example@example.comcom";
        String wrongEmailCase3 = "example@example..com";
        String wrongEmailCase4 = "example@example,com";

        // When
        boolean resultOfChecking1 = VerificationMail.checkEmailFormat(wrongEmailCase1);
        boolean resultOfChecking2 = VerificationMail.checkEmailFormat(wrongEmailCase2);
        boolean resultOfChecking3 = VerificationMail.checkEmailFormat(wrongEmailCase3);
        boolean resultOfChecking4 = VerificationMail.checkEmailFormat(wrongEmailCase4);

        // Then
        assertThat(resultOfChecking1).isEqualTo(false);
        assertThat(resultOfChecking2).isEqualTo(false);
        assertThat(resultOfChecking3).isEqualTo(false);
        assertThat(resultOfChecking4).isEqualTo(false);
    }
}