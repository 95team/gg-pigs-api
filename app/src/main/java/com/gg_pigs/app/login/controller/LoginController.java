package com.gg_pigs.app.login.controller;

import com.gg_pigs.app.login.dto.LoginDto;
import com.gg_pigs.app.login.service.LoginExtendService;
import com.gg_pigs.app.user.dto.RetrieveDtoUser;
import com.gg_pigs.global.dto.ApiResponse;
import com.gg_pigs.global.security.GPUserDetails;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final LoginExtendService loginService;

    @PostMapping("/api/v1/login")
    public ApiResponse login(HttpServletResponse response,
                             @RequestBody LoginDto.RequestDto requestDto) {
        Cookie loginCookie = loginService.login(requestDto.toLoginObject());
        response.addCookie(loginCookie);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), true);
    }

    @PostMapping("/api/v1/logout")
    public ApiResponse logout(HttpServletResponse response) {
        Cookie logoutCookie = loginService.logout();
        response.addCookie(logoutCookie);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), new ArrayList<>());
    }

    @GetMapping("/api/v1/login-users")
    public ApiResponse checkLoginUser(@NonNull @AuthenticationPrincipal GPUserDetails gpUserDetails) {
        Objects.requireNonNull(gpUserDetails, "인증 정보가 존재하지 않습니다. (Your authentication is empty)");
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), RetrieveDtoUser.of(gpUserDetails.getUser()));
    }
}
