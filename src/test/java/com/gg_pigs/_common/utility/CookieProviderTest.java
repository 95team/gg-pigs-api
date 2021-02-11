package com.gg_pigs._common.utility;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.Cookie;

@SpringBootTest(
        classes = {
                CookieProvider.class
        }
)
class CookieProviderTest {

    @Autowired CookieProvider cookieProvider;

    @Test
    public void When_call_generateCookie_Then_return_cookie() {
        // Given
        String name = "jwt";
        String value = "This is a token";

        // When
        Cookie cookie = cookieProvider.generateCookie(name, value);

        // Then
        Assertions.assertThat(cookie.getName()).isEqualTo(name);
        Assertions.assertThat(cookie.getValue()).isEqualTo(value);
    }

    @Test
    public void When_call_destroyCookie_Then_set_expiry_0() {
        // Given
        String name = "jwt";
        String value = null;

        // When
        Cookie cookie = cookieProvider.destroyCookie(name, value);

        // Then
        Assertions.assertThat(cookie.getName()).isEqualTo(name);
        Assertions.assertThat(cookie.getValue()).isNull();
        Assertions.assertThat(cookie.getMaxAge()).isEqualTo(0);
    }
}