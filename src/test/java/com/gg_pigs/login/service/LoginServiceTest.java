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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(
        classes = {
                JwtProvider.class,
                CookieProvider.class,
                LoginService.class
        }
)
class LoginServiceTest {

    @Autowired JwtProvider jwtProvider;
    @Autowired CookieProvider cookieProvider;
    @Autowired LoginService loginService;

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

        // Configuration of UserRepository
        Mockito.when(userRepository.findUserByEmail(anyString())).thenReturn(java.util.Optional.of(user));

        // Configuration of UserSaltRepository
        Mockito.when(userSaltRepository.findUserSaltByUserId(anyLong())).thenReturn(java.util.Optional.of(userSalt));
    }

    @Test
    public void When_call_login_Then_return_LoginResult() {
        // Given
        RequestDtoLogin correctRequestDtoLogin = new RequestDtoLogin(loginEmail, correctLoginPassword);
        RequestDtoLogin wrongRequestDtoLogin = new RequestDtoLogin(loginEmail, wrongLoginPassword);
        Mockito.when(userSalt.getDigest()).thenReturn(digest);

        // When
        LoginResult correctLoginResult = loginService.login(correctRequestDtoLogin);
        LoginResult wrongLoginResult = loginService.login(wrongRequestDtoLogin);

        // Then
        assertThat(correctLoginResult.isLogin()).isEqualTo(true);
        assertThat(correctLoginResult.getRole()).isEqualTo(userRole);
        assertThat(correctLoginResult.getEmail()).isEqualTo(loginEmail);

        assertThat(wrongLoginResult.isLogin()).isEqualTo(false);
        assertThat(wrongLoginResult.getRole()).isEqualTo(userRole);
        assertThat(wrongLoginResult.getEmail()).isEqualTo(loginEmail);
    }

    @Test
    public void When_call_checkPw_Then_check_the_password() {
        // Given // When
        boolean correctResultOfCheckPw = loginService.checkPw(correctLoginPassword, digest);
        boolean wrongResultOfCheckPw = loginService.checkPw(wrongLoginPassword, digest);

        // Then
        assertThat(correctResultOfCheckPw).isEqualTo(true);
        assertThat(wrongResultOfCheckPw).isEqualTo(false);
    }
}