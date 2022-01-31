package com.gg_pigs.global.utility;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = {
                JwtProvider.class,
        }
)
class JwtProviderTest {

    @Autowired JwtProvider jwtProvider;

    private long expirationAge = 60 * 60 * 1000;
    private String role = "ROLE_USER";
    private String audience = "pigs95team";
    private String subject = "subject";

    @Test
    public void When_call_generateToken_Then_return_JWT() {
        // Given // When
        String jwt = jwtProvider.generateToken(subject, audience, role);

        // Then
        assertThat(jwt.getClass()).isEqualTo(String.class);
    }

    @Test
    public void When_call_getPayloadFromToken_Then_return_payload() {
        // Given
        String jwt = jwtProvider.generateToken(subject, audience, role);

        // When
        Claims parsedJwt = jwtProvider.getPayloadFromToken(jwt);

        // Then
        assertThat(parsedJwt.get("role")).isEqualTo(role);
        assertThat(parsedJwt.getSubject()).isEqualTo(subject);
        assertThat(parsedJwt.getAudience()).isEqualTo(audience);
        assertThat(parsedJwt.getExpiration().getTime() - parsedJwt.getIssuedAt().getTime()).isEqualTo(expirationAge);
    }
}