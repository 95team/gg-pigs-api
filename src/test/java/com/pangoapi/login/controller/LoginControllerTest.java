package com.pangoapi.login.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pangoapi._common.utility.JwtProvider;
import com.pangoapi.login.dto.LoginResult;
import com.pangoapi.login.dto.RequestDtoLogin;
import com.pangoapi.login.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

@WebMvcTest(value = LoginController.class)
class LoginControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean JwtProvider jwtProvider;
    @MockBean LoginService loginService;

    @Mock LoginResult loginResult;

    private RequestDtoLogin requestDtoLogin;
    private String loginRole = "ROLE_USER";
    private String loginEmail = "pigs95team@gmail.com";
    private String loginPassword = "thisisapassword";
    private String token = "thisisatoken";

    @BeforeEach
    public void setUp() {
        // Configuration of Dto (RequestDtoLogin)
        requestDtoLogin = new RequestDtoLogin(loginEmail, loginPassword);

        // Configuration of LoginResult
        Mockito.when(loginResult.isLogin()).thenReturn(true);
        Mockito.when(loginResult.getRole()).thenReturn(loginRole);
        Mockito.when(loginResult.getEmail()).thenReturn(loginEmail);

        // Configuration of LoginService
        Mockito.when(loginService.login(any(RequestDtoLogin.class))).thenReturn(loginResult);
    }

    @Test
    public void 로그인() throws Exception {
        // Given
        String content = objectMapper.writeValueAsString(requestDtoLogin);

        // When // Then
        mockMvc.perform(post("/api/v1/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }
}