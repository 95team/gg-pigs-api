package com.gg_pigs._common.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtProvider {

    @Value("${application.jwt.security-key}")
    private String secretKey;

    @Value("${application.jwt.expiration-age}")
    private long expirationAge;

    /**
     * [Note]
     * 1. @Value 어노테이션을 이용하기 위해서는 setter 가 필요합니다.
     * */
    public void setSecretKey(String secretKey) { this.secretKey = secretKey; }
    public void setExpirationAge(long expirationAge) { this.expirationAge = expirationAge; }

    public String generateToken(String subject, String audience, String role) {
        String token;

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + expirationAge);

        try {
            Claims claims = Jwts.claims()
                    .setSubject(subject)
                    .setAudience(audience)
                    .setIssuedAt(issuedAt)
                    .setExpiration(expiration);

            token = Jwts.builder()
                    .setHeaderParam("typ", "JWT")
                    .setHeaderParam("alg", "HS256")
                    .setClaims(claims)
                    .signWith(key)
                    .claim("role", role)
                    .compact();
        } catch (Exception e) {
            e.printStackTrace();

            throw new InternalException("인증 토큰을 생성할 수 없습니다. (Can't generate the token)");
        }

        return token;
    }

    public Claims getPayloadFromToken(String token) {
        Jws<Claims> parsedToken = this.parseToken(token);

        return parsedToken.getBody();
    }

    private Jws<Claims> parseToken(String token) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

        Jws<Claims> parsedToken = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

        return parsedToken;
    }
}
