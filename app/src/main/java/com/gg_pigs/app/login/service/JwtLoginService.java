package com.gg_pigs.app.login.service;

import com.gg_pigs.global.exception.GPLoginFailureException;
import com.gg_pigs.global.utility.CookieProvider;
import com.gg_pigs.global.utility.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

/**
 * [References]
 * 1. https://velog.io/@ljinsk3/JWT%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-Bearer%EB%B0%A9%EC%8B%9D%EC%9C%BC%EB%A1%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8%ED%95%98%EA%B8%B0
 * */

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtLoginService implements LoginService {

    private final JwtProvider jwtProvider;
    private final CookieProvider cookieProvider;

    @Override
    public Cookie login(Login login) {
        String email = login.getEmail();
        String password = login.getPassword();
        String digest = login.getDigest();
        String role = login.getRole();

        if(!this.checkPw(password, digest)) {
            throw new GPLoginFailureException("로그인에 실패했습니다.");
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
