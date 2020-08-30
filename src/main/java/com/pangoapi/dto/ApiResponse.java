package com.pangoapi.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ApiResponse<T> {
    private T code;
    private T message;
    private T data;

    @Builder
    public ApiResponse(T code, T message, T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ApiResponse of(HttpStatus e) {
        return ApiResponse.builder()
                .code(e.value())
                .message(e.getReasonPhrase())
                .data(new ArrayList<>())
                .build();
    }

    public static ApiResponse of(HttpStatus e, String message) {
        return ApiResponse.builder()
                .code(e.value())
                .message(e.getReasonPhrase())
                .data(message)
                .build();
    }
}
