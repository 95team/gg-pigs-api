package com.gg_pigs.login.controller;

import com.gg_pigs._common.dto.ApiResponse;
import com.gg_pigs._common.exception.LoginFailureException;
import com.gg_pigs._common.utility.CookieProvider;
import com.gg_pigs._common.utility.JwtProvider;
import com.gg_pigs.login.dto.LoginResult;
import com.gg_pigs.login.dto.RequestDtoLogin;
import com.gg_pigs.login.dto.ResponseDtoLogin;
import com.gg_pigs.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

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

    @PostMapping("/api/v1/logout")
    public ApiResponse<ResponseDtoLogin> logout(HttpServletResponse response) {

        Cookie logoutCookie = cookieProvider.destroyCookie("jwt", null);
        response.addCookie(logoutCookie);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), new ArrayList<>());
    }
}
