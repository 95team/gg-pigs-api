package com.gg_pigs.login.service;

import com.gg_pigs._common.exception.LoginFailureException;
import com.gg_pigs._common.utility.CookieProvider;
import com.gg_pigs._common.utility.JwtProvider;
import com.gg_pigs.login.dto.RequestDtoLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

/**
 * [References]
 * 1. https://velog.io/@ljinsk3/JWT%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-Bearer%EB%B0%A9%EC%8B%9D%EC%9C%BC%EB%A1%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8%ED%95%98%EA%B8%B0
 * */

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class JwtLoginService implements LoginService {

    private final JwtProvider jwtProvider;
    private final CookieProvider cookieProvider;

    @Override
    public Cookie login(RequestDtoLogin requestDtoLogin) {
        String email = requestDtoLogin.getEmail();
        String password = requestDtoLogin.getPassword();
        String digest = requestDtoLogin.getDigest();
        String role = requestDtoLogin.getRole();

        if(!this.checkPw(password, digest)) {
            throw new LoginFailureException("로그인에 실패했습니다.");
        }

        String subject = "login";
        String audience = email;

        String token = jwtProvider.generateToken(subject, audience, role);
        Cookie cookie = cookieProvider.generateLoginCookie(token);

        return cookie;
    }

    @Override
    public Cookie logout() {
        return cookieProvider.destroyLoginCookie();
    }

    public boolean checkPw(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
