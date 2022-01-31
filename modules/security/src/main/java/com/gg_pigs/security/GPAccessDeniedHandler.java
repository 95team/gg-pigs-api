package com.gg_pigs.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public interface GPAccessDeniedHandler extends AccessDeniedHandler {
    @Override
    default void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        accessDeniedException.printStackTrace();

        String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";

        Map<String, Object> errorResponse = new LinkedHashMap<String, Object>() {{
            put("status", HttpStatus.FORBIDDEN.value());
            put("message", "허용되지 않은 요청입니다. (Your authentication level is not allowed)");
            put("data", null);
        }};

        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
