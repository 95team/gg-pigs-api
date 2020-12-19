package com.pangoapi._common.utility;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

/**
 * [References]
 * 1. https://chodragon9.github.io/blog/cors-and-http-cookie/
 * 2. https://evan-moon.github.io/2020/05/21/about-cors/
 * */

@Service
public class CookieProvider {

    private String uri = "/";
    private int expiry = 3600;
    private boolean httpOnly = true;

    public Cookie generateCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(this.uri);
        cookie.setMaxAge(this.expiry);
        cookie.setHttpOnly(this.httpOnly);

        return cookie;
    }
}
