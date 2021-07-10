package com.gg_pigs.login.controller;

import com.gg_pigs._common.dto.ApiResponse;
import com.gg_pigs._common.exception.BadRequestException;
import com.gg_pigs._common.utility.EmailUtil;
import com.gg_pigs._common.utility.JwtProvider;
import com.gg_pigs.login.dto.RequestDtoLogin;
import com.gg_pigs.login.service.LoginService;
import com.gg_pigs.user.dto.RetrieveDtoUser;
import com.gg_pigs.user.service.UserService;
import com.gg_pigs.userSalt.dto.RetrieveDtoUserSalt;
import com.gg_pigs.userSalt.service.UserSaltService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final UserService userService;
    private final UserSaltService userSaltService;
    private final LoginService loginService;
    private final JwtProvider jwtProvider;

    @PostMapping("/api/v1/login")
    public ApiResponse login(HttpServletResponse response, @RequestBody RequestDtoLogin requestDtoLogin) {
        String email = requestDtoLogin.getEmail();
        String password = requestDtoLogin.getPassword();

        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            throw new BadRequestException("적절하지 않은 요청입니다. (Please check the required value)");
        }
        if(!EmailUtil.checkEmailFormat(email)) {
            throw new BadRequestException("적절하지 않은 이메일 형식 입니다. (Please check the email)");
        }

        RetrieveDtoUser userDto = userService.readByEmail(email);
        RetrieveDtoUserSalt userSaltDto = userSaltService.read(userDto.getUserId());

        RequestDtoLogin loginDto = RequestDtoLogin.builder()
                .email(email)
                .password(password)
                .digest(userSaltDto.getDigest())
                .role(userDto.getRole())
                .build();

        Cookie loginCookie = loginService.login(loginDto);
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
    public ApiResponse checkLoginUser(@CookieValue("${application.cookie.login-cookie-name}") String token) {
        Claims payloadFromToken = jwtProvider.getPayloadFromToken(token);
        RetrieveDtoUser retrieveDtoUser = userService.readByEmail(payloadFromToken.getAudience());

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), retrieveDtoUser);
    }
}
