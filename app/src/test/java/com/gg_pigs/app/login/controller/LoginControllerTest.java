package com.gg_pigs.app.login.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gg_pigs._common.SecuritySetUp4ControllerTest;
import com.gg_pigs.app.login.dto.LoginDto;
import com.gg_pigs.app.login.service.LoginExtendService;
import com.gg_pigs.app.login.service.LoginService;
import com.gg_pigs.app.user.dto.RetrieveDtoUser;
import com.gg_pigs.app.userSalt.dto.RetrieveDtoUserSalt;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = LoginController.class)
class LoginControllerTest extends SecuritySetUp4ControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    LoginExtendService loginService;

    @Mock
    RetrieveDtoUser retrieveDtoUser;
    @Mock
    RetrieveDtoUserSalt retrieveDtoUserSalt;

    private final Long userId = 1L;
    private final String userEmail = "pigs95team@gmail.com";
    private final String userPassword = "thisisapassword";
    private final String userRole = "role";
    private final String userDigest = "digest";
    private final String cookieName = "test";
    private final String cookieValue = "test";
    private final Cookie loginCookie = new Cookie(cookieName, "test");
    private final Cookie logoutCookie = new Cookie(cookieName, null);

    @BeforeEach
    public void setUp() {
        // Configuration of LoginService
        Mockito.when(loginService.login(any(LoginService.Login.class))).thenReturn(loginCookie);
        Mockito.when(loginService.logout()).thenReturn(logoutCookie);

        // Configuration of UserService
        Mockito.when(retrieveDtoUser.getUserId()).thenReturn(userId);
        Mockito.when(retrieveDtoUser.getRole()).thenReturn(userRole);

        // Configuration of UserSaltService
        Mockito.when(retrieveDtoUserSalt.getDigest()).thenReturn(userDigest);
    }

    @Test
    public void When_로그인_Then_cookie_has_value() throws Exception {
        // Given
        LoginDto.RequestDto requestDto = LoginDto.RequestDto.builder().email(userEmail).password(userPassword).build();
        String content = objectMapper.writeValueAsString(requestDto);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/login")
                                                                   .contentType(MediaType.APPLICATION_JSON)
                                                                   .content(content))
                                                  .andExpect(status().isOk())
                                                  .andDo(print())
                                                  .andReturn().getResponse();

        // Then
        Assertions.assertThat(response.getCookie(cookieName).getValue()).isEqualTo(cookieValue);
    }

    @Test
    public void When_로그아웃_Then_cookie_is_null() throws Exception {
        // Given // When
        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/logout")
                                                                   .contentType(MediaType.APPLICATION_JSON)
                                                                   .accept(MediaType.APPLICATION_JSON))
                                                  .andExpect(status().isOk())
                                                  .andDo(print())
                                                  .andReturn().getResponse();

        // Then
        Assertions.assertThat(response.getCookie(cookieName).getValue()).isNull();
    }
}