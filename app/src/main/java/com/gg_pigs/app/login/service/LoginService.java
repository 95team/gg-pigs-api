package com.gg_pigs.app.login.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.servlet.http.Cookie;

public interface LoginService {

    /**
     * Login method
     * @return Login cookie
     * */
    Cookie login(Login login);

    /**
     * Logout method
     * @return Logout cookie (Expiration is 0)
     * */
    Cookie logout();

    @ToString(of = {"email", "role"})
    @Builder
    @Getter
    class Login {
        private String email;
        @JsonIgnore
        private String password;
        @JsonIgnore
        private String digest;
        private String role;
    }
}
