package com.gg_pigs._common.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

import static com.gg_pigs._common.CommonDefinition.DEFAULT_CACHE_MAX_AGE;

@ControllerAdvice
public class CacheControllerAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) serverHttpRequest;
        ServletServerHttpResponse servletServerHttpResponse = (ServletServerHttpResponse) serverHttpResponse;

        HttpServletRequest request = servletServerHttpRequest.getServletRequest();
        HttpServletResponse response = servletServerHttpResponse.getServletResponse();

        int responseStatus = response.getStatus();
        String requestMethod = request.getMethod();

        if( requestMethod.equals(HttpMethod.GET.name()) && (responseStatus < HttpStatus.MULTIPLE_CHOICES.value()) ) {
            response.setHeader(HttpHeaders.CACHE_CONTROL, CacheControl.maxAge(DEFAULT_CACHE_MAX_AGE, TimeUnit.SECONDS).getHeaderValue());
        }

        return body;
    }
}
