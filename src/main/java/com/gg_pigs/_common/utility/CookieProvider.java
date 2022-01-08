package com.gg_pigs._common.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

/**
 * [References]
 * 1. https://chodragon9.github.io/blog/cors-and-http-cookie/
 * 2. https://evan-moon.github.io/2020/05/21/about-cors/
 * */

@Component
public class CookieProvider {

    private final String uri = "/";
    private final int expiry = 3600;
    private final boolean isHttpOnly = true;
    private final boolean isSecure = false;
    private final String domain = "gg-pigs.com";

    @Value("${application.cookie.login-cookie-name}")
    private String loginCookieName;

    public Cookie generateLoginCookie(String value) {
        return this.generateCookie(this.loginCookieName, value);
    }

    public Cookie generateCookie(String name, String value) {
        return this.generateCookie(name, value, this.uri, this.expiry, this.isHttpOnly, this.isSecure, this.domain);
    }

    private Cookie generateCookie(String name, String value, String uri, int expiry, boolean httpOnly, boolean secure, String domain) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(uri);
        cookie.setMaxAge(expiry);
        cookie.setHttpOnly(httpOnly);
        cookie.setSecure(secure);
        cookie.setDomain(domain);

        return cookie;
    }

    public Cookie destroyCookie(String name, String value) {
        /**
         * [Note]
         * 1. MaxAge 를 '0' 으로 설정하여 쿠키를 삭제합니다.
         * */
        return this.generateCookie(name, value, this.uri, 0, this.isHttpOnly, this.isSecure, this.domain);
    }

    public Cookie destroyLoginCookie() {
        return this.destroyCookie(this.loginCookieName, null);
    }
}
