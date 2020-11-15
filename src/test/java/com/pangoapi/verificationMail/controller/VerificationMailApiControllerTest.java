package com.pangoapi.verificationMail.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pangoapi._common.utility.MailHandler;
import com.pangoapi.verificationMail.dto.RequestDtoVerificationMail;
import com.pangoapi.verificationMail.dto.ResponseDtoVerificationMail;
import com.pangoapi.verificationMail.service.VerificationMailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = VerificationMailApiController.class)
class VerificationMailApiControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean VerificationMailService verificationMailService;

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

        Mockito.when(verificationMailService.sendVerificationEmail(any(MailHandler.class), any(ResponseDtoVerificationMail.class), any(RequestDtoVerificationMail.class))).thenReturn(responseDtoVerificationMail);
    }

    @Test
    public void verificationMail_한건_전송() throws Exception {
        String content = objectMapper.writeValueAsString(requestDtoVerificationMail);

        mockMvc.perform(post("/api/v1/verification-mails")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }
}