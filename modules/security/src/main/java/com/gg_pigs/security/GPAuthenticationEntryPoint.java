package com.gg_pigs.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public interface GPAuthenticationEntryPoint extends AuthenticationEntryPoint {
    @Override
    default void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";

        Map<String, Object> errorResponse = new LinkedHashMap<String, Object>() {{
            put("status", HttpStatus.UNAUTHORIZED.value());
            put("message", "인증 정보가 존재하지 않습니다. (Your authentication is not valid)");
            put("data", null);
        }};

        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
