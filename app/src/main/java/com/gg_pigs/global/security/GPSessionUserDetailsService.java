package com.gg_pigs.global.security;

import com.gg_pigs.app.login.service.LoginService;
import com.gg_pigs.app.user.entity.User;
import com.gg_pigs.app.user.repository.UserRepository;
import com.gg_pigs.modules.redis.RedisClient;
import com.gg_pigs.security.GPAuthenticationToken;
import com.gg_pigs.security.GPSecurityException;
import com.gg_pigs.security.GPUserDetailsService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class GPSessionUserDetailsService implements GPUserDetailsService {

    private final Gson gson;
    private final RedisClient redisClient;
    private final UserRepository userRepository;

    @Override
    public GPUserDetails loadUserByUsername(String key) throws GPSecurityException {
        String value = redisClient.getStringValue(key);
        if(value == null) {
            throw new GPSecurityException("인증 정보가 존재하지 않습니다. (Your authentication-i is empty)");
        }

        LoginService.Login login = gson.fromJson(value, LoginService.Login.class);
        User user = userRepository.findUserByEmail(login.getEmail())
                                  .orElseThrow(() -> new GPSecurityException("인증 사용자가 존재하지 않습니다. (Your information-u is empty)"));

        return GPUserDetails.of(user);
    }

    @Override
    public GPUserDetails loadUserByHttpServletRequest(HttpServletRequest request) throws GPSecurityException {
        Cookie[] cookies = request.getCookies();
        if(cookies == null) {
            throw new GPSecurityException("인증 정보가 존재하지 않습니다. (Your authentication-c1 is empty)");
        }

        Cookie loginCookie = Arrays.stream(cookies)
                                   .filter((cookie) -> cookie.getName().equals("gg-login"))
                                   .findFirst()
                                   .orElseThrow(() -> new GPSecurityException("인증 정보가 존재하지 않습니다. (Your authentication-c2 is empty)"));

        return this.loadUserByUsername(loginCookie.getValue());
    }

    @Override
    public GPAuthenticationToken loadAuthenticationByUser(com.gg_pigs.security.GPUserDetails gpUserDetails) throws GPSecurityException {
        return new GPAuthenticationToken(gpUserDetails, gpUserDetails.getAuthorities());
    }
}
