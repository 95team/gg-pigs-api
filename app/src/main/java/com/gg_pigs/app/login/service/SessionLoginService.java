package com.gg_pigs.app.login.service;

import com.gg_pigs.app.user.entity.User;
import com.gg_pigs.app.user.repository.UserRepository;
import com.gg_pigs.app.userSalt.entity.UserSalt;
import com.gg_pigs.app.userSalt.repository.UserSaltRepository;
import com.gg_pigs.global.exception.GPBadRequestException;
import com.gg_pigs.global.exception.GPLoginFailureException;
import com.gg_pigs.global.utility.CookieProvider;
import com.gg_pigs.modules.redis.RedisClient;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SessionLoginService implements LoginExtendService {

    private final Gson gson;
    private final RedisClient redisClient;
    private final CookieProvider cookieProvider;

    private final UserRepository userRepository;
    private final UserSaltRepository userSaltRepository;

    @Override
    public Cookie login(Login login) {
        User user = userRepository.findUserByEmail(login.getEmail()).orElseThrow(() -> new EntityNotFoundException("[login] 사용자 정보를 조회할 수 없습니다."));
        UserSalt userSalt = userSaltRepository.findUserSaltByUserId(user.getId()).orElseThrow(() -> new EntityNotFoundException("[login] 사용자 정보(salt)를 조회할 수 없습니다."));

        if(!this.checkPw(login.getPassword(), userSalt.getDigest())) {
            throw new GPLoginFailureException("[login] 로그인에 실패했습니다.");
        }

        Login login4SessionStore = Login.builder()
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
        String key = UUID.randomUUID().toString();
        String value = gson.toJson(login4SessionStore);
        redisClient.storeStringValueForSeconds(key, value, 3600);

        return cookieProvider.generateLoginCookie(key);
    }

    @Override
    public Cookie logout() {
        return cookieProvider.destroyLoginCookie();
    }

    @Override
    public Login getLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String key = null;
        String value = null;

        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("gg-login")) {
                key = cookie.getValue();
            }
        }
        if(key == null) {
            throw new GPBadRequestException("[login] 로그인 정보를 조회할 수 없습니다.");
        }

        value = redisClient.getStringValue(key);
        if(value == null) {
            throw new GPBadRequestException("[login] 로그인 정보를 조회할 수 없습니다.");
        }

        return gson.fromJson(value, Login.class);
    }

    private boolean checkPw(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
