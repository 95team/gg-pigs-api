package com.gg_pigs.app.verificationMail.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gg_pigs._common.SecuritySetUp4ControllerTest;
import com.gg_pigs.app.verificationMail.dto.RequestDtoVerificationMail;
import com.gg_pigs.app.verificationMail.dto.ResponseDtoVerificationMail;
import com.gg_pigs.app.verificationMail.service.VerificationMailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = VerificationMailApiController.class)
class VerificationMailApiControllerTest extends SecuritySetUp4ControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean VerificationMailService verificationMailService;
    @MockBean JavaMailSender javaMailSender;

    private RequestDtoVerificationMail requestDtoVerificationMail;
    private ResponseDtoVerificationMail responseDtoVerificationMail;

    @BeforeEach
    void setUp() throws Exception {
        String sender = "pigs95team@gmail.com";
        String receiver = "pigs95team@gmail.com";
        String verificationCode = "620124";

        requestDtoVerificationMail = RequestDtoVerificationMail.builder().receiver(receiver).build();
        responseDtoVerificationMail = new ResponseDtoVerificationMail();
        responseDtoVerificationMail.changeToSuccess(sender, receiver, verificationCode);

        Mockito.when(verificationMailService.send4EmailVerification(any(RequestDtoVerificationMail.class))).thenReturn(responseDtoVerificationMail);
    }

    @DisplayName("[테스트] sendVerificationEmail()")
    @Test
    public void Test_sendVerificationEmail() throws Exception {
        String content = objectMapper.writeValueAsString(requestDtoVerificationMail);

        mockMvc.perform(post("/api/v1/verification-mails")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(content))
               .andExpect(status().isOk())
               .andDo(print());
    }
}