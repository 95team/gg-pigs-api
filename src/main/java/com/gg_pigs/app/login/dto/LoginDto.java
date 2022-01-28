package com.gg_pigs.app.login.dto;

import com.gg_pigs.app.login.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LoginDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class RequestDto {
        String email;
        String password;

        public LoginService.Login toLoginObject() {
            return LoginService.Login.builder()
                    .email(email)
                    .password(password)
                    .build();
        }
    }
}
