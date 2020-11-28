package com.pangoapi.login.controller;

import com.pangoapi._common.dto.ApiResponse;
import com.pangoapi._common.exception.LoginFailureException;
import com.pangoapi.login.dto.LoginResult;
import com.pangoapi.login.dto.RequestDtoLogin;
import com.pangoapi.login.dto.ResponseDtoLogin;
import com.pangoapi.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/api/v1/login")
    public ApiResponse<ResponseDtoLogin> login(@RequestBody RequestDtoLogin requestDtoLogin) {
        ResponseDtoLogin responseDtoLogin = new ResponseDtoLogin();

        LoginResult loginResult = loginService.login(requestDtoLogin);

        if(loginResult.isLogin() == true) {
            String subject = "login";
            String audience = loginResult.getEmail();
            String role = loginResult.getRole();

            responseDtoLogin.setIsLogin(true);
            responseDtoLogin.setToken(loginService.generateJwt(subject, audience, role));
        }
        else {
            throw new LoginFailureException("로그인에 실패했습니다.");
        }

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), responseDtoLogin);
    }
}
