package com.pangoapi.login.controller;

import com.pangoapi._common.dto.ApiResponse;
import com.pangoapi._common.exception.LoginFailureException;
import com.pangoapi._common.utility.CookieProvider;
import com.pangoapi._common.utility.JwtProvider;
import com.pangoapi.login.dto.LoginResult;
import com.pangoapi.login.dto.RequestDtoLogin;
import com.pangoapi.login.dto.ResponseDtoLogin;
import com.pangoapi.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final JwtProvider jwtProvider;
    private final CookieProvider cookieProvider;

    private final LoginService loginService;

    @PostMapping("/api/v1/login")
    public ApiResponse<ResponseDtoLogin> login(HttpServletResponse response, @RequestBody RequestDtoLogin requestDtoLogin) {
        ResponseDtoLogin responseDtoLogin = new ResponseDtoLogin();

        LoginResult loginResult = loginService.login(requestDtoLogin);

        if(loginResult.isLogin() == true) {
            responseDtoLogin.successLogin();

            String subject = "login";
            String audience = loginResult.getEmail();
            String role = loginResult.getRole();

            String token = jwtProvider.generateToken(subject, audience, role);
            Cookie cookie = cookieProvider.generateCookie("jwt", token);

            response.addCookie(cookie);
        }
        else {
            throw new LoginFailureException("로그인에 실패했습니다.");
        }

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), responseDtoLogin);
    }
}
