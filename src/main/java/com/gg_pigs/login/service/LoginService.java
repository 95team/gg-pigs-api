package com.gg_pigs.login.service;

import com.gg_pigs.login.dto.RequestDtoLogin;

import javax.servlet.http.Cookie;

public interface LoginService {

    /**
     * Login method
     * @return Login cookie
     * */
    Cookie login(RequestDtoLogin loginDto);

    /**
     * Logout method
     * @return Logout cookie (Expiration is 0)
     * */
    Cookie logout();
}
