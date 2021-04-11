package com.gg_pigs.login.service;

import com.gg_pigs._common.utility.CookieProvider;
import com.gg_pigs._common.utility.JwtProvider;
import com.gg_pigs.login.dto.LoginResult;
import com.gg_pigs.login.dto.RequestDtoLogin;
import com.gg_pigs.user.entity.User;
import com.gg_pigs.user.repository.UserRepository;
import com.gg_pigs.userSalt.entity.UserSalt;
import com.gg_pigs.userSalt.repository.UserSaltRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.servlet.http.Cookie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(
        classes = {
                JwtProvider.class,
                CookieProvider.class,
                LoginServiceJwtImpl.class
        }
)
class LoginServiceTest {

    @Autowired JwtProvider jwtProvider;
    @Autowired CookieProvider cookieProvider;
    @Autowired LoginServiceJwtImpl loginService;

    @MockBean UserRepository userRepository;
    @MockBean UserSaltRepository userSaltRepository;

    @Mock User user;
    @Mock UserSalt userSalt;

    private long userId = 1L;
    private String userRole = "ROLE_USER";

    private String loginEmail = "pigs95team@gmail.com";
    private String loginPassword = "thisisapassword";

    private String correctLoginPassword = loginPassword;
    private String wrongLoginPassword = loginPassword + "a";

    private String salt = UserSalt.generateSalt();
    private String digest = UserSalt.generateDigest(loginPassword, salt);

    @BeforeEach
    void setUp() {
        // Configuration of User
        Mockito.when(user.getId()).thenReturn(userId);
        Mockito.when(user.getRole()).thenReturn(userRole);
        Mockito.when(user.getEmail()).thenReturn(loginEmail);

        // Configuration of Digest
        Mockito.when(userSalt.getDigest()).thenReturn(digest);

        // Configuration of UserRepository
        Mockito.when(userRepository.findUserByEmail(anyString())).thenReturn(java.util.Optional.of(user));

        // Configuration of UserSaltRepository
        Mockito.when(userSaltRepository.findUserSaltByUserId(anyLong())).thenReturn(java.util.Optional.of(userSalt));
    }

    @Test
    public void When_success_login_Then_return_cookie() {
        // Given
        RequestDtoLogin correctLoginDto = new RequestDtoLogin(loginEmail, correctLoginPassword);

        // When
        Cookie loginCookie = loginService.login(correctLoginDto);

        // Then
        System.out.println(loginCookie);
    }

    @Test
    public void When_failed_login_Then_throw_exception() {
        // Given
        RequestDtoLogin wrongLoginDto = new RequestDtoLogin(loginEmail, wrongLoginPassword);

        // When
        Cookie loginCookie = loginService.login(wrongLoginDto);

        // Then

        System.out.println(loginCookie);
    }
}