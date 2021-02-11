package com.gg_pigs.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResponseDtoLogin {

    boolean isLogin = false;

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public void successLogin() {
        this.isLogin = true;
    }

    public void failedLogin() {
        this.isLogin = false;
    }
}
