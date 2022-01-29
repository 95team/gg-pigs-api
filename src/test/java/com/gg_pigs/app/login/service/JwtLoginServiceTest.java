package com.gg_pigs.app.login.service;

import com.gg_pigs.app.user.entity.User;
import com.gg_pigs.app.user.entity.UserRole;
import com.gg_pigs.app.userSalt.entity.UserSalt;
import com.gg_pigs.global.exception.GPLoginFailureException;
import com.gg_pigs.global.utility.CookieProvider;
import com.gg_pigs.global.utility.JwtProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.Cookie;

@SpringBootTest(
        classes = {
                JwtProvider.class,
                CookieProvider.class,
                JwtLoginService.class
        }
)
class JwtLoginServiceTest {

    @Autowired JwtProvider jwtProvider;
    @Autowired CookieProvider cookieProvider;
    @Autowired JwtLoginService jwtLoginService;

    @Mock User user;
    @Mock UserSalt userSalt;

    private final long userId = 1L;
    private final UserRole userRole = UserRole.ROLE_USER;

    private final String loginEmail = "pigs95team@gmail.com";
    private final String loginPassword = "thisisapassword";

    private final String salt = UserSalt.generateSalt();
    private final String digest = UserSalt.generateDigest(loginPassword, salt);

    @BeforeEach
    void setUp() {
        // Configuration of User
        Mockito.when(user.getId()).thenReturn(userId);
        Mockito.when(user.getRole()).thenReturn(userRole);
        Mockito.when(user.getEmail()).thenReturn(loginEmail);

        // Configuration of Digest
        Mockito.when(userSalt.getDigest()).thenReturn(digest);
    }

    @Test
    public void When_success_login_Then_return_cookie() {
        // Given
        String correctLoginPassword = loginPassword;
        LoginService.Login correctLoginDto = LoginService.Login.builder()
                .email(loginEmail)
                .password(correctLoginPassword)
                .digest(digest)
                .role(userRole.name())
                .build();

        // When
        Cookie loginCookie = jwtLoginService.login(correctLoginDto);

        // Then
        Assertions.assertThat(loginCookie.getValue()).isNotNull();
    }

    @Test()
    public void When_failed_login_Then_throw_exception() {
        // Given
        String wrongLoginPassword = loginPassword + "a";
        LoginService.Login wrongLoginDto = LoginService.Login.builder()
                .email(loginEmail)
                .password(wrongLoginPassword)
                .digest(digest)
                .role(userRole.name())
                .build();

        // When
        try {
            Cookie loginCookie = jwtLoginService.login(wrongLoginDto);
        } catch (GPLoginFailureException GPLoginFailureException) {
            return;
        }

        // Then
        Assertions.fail("LoginFailureException 예외가 발생해야 합니다.");
    }

    @Test()
    public void When_logout_Then_return_null_cookie() {
        // Given // When
        Cookie logoutCookie = jwtLoginService.logout();

        // Then
        Assertions.assertThat(logoutCookie.getValue()).isNull();
    }
}