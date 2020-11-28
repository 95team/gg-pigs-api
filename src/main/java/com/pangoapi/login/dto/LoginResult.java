package com.pangoapi.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class LoginResult {

    boolean isLogin = false;
    String email;
    String role;

    public LoginResult(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public void successLogin() {
        this.isLogin = true;
    }

    public void failedLogin() {
        this.isLogin = false;
    }
}
