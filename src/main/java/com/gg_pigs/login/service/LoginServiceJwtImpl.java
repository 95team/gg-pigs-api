package com.gg_pigs.login.service;

import com.gg_pigs._common.CommonDefinition;
import com.gg_pigs._common.exception.BadRequestException;
import com.gg_pigs._common.exception.LoginFailureException;
import com.gg_pigs._common.utility.CookieProvider;
import com.gg_pigs._common.utility.EmailUtility;
import com.gg_pigs._common.utility.JwtProvider;
import com.gg_pigs.login.dto.LoginResult;
import com.gg_pigs.login.dto.RequestDtoLogin;
import com.gg_pigs.user.entity.User;
import com.gg_pigs.user.repository.UserRepository;
import com.gg_pigs.userSalt.entity.UserSalt;
import com.gg_pigs.userSalt.repository.UserSaltRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.Cookie;

/**
 * [References]
 * 1. https://velog.io/@ljinsk3/JWT%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-Bearer%EB%B0%A9%EC%8B%9D%EC%9C%BC%EB%A1%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8%ED%95%98%EA%B8%B0
 * */

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LoginServiceImpl implements LoginService {

    private final JwtProvider jwtProvider;
    private final CookieProvider cookieProvider;
    private final UserRepository userRepository;
    private final UserSaltRepository userSaltRepository;

    @Override
    public Cookie login(RequestDtoLogin loginDto) {
        String loginEmail = loginDto.getEmail();
        String loginPassword = loginDto.getPassword();

        if(StringUtils.isEmpty(loginEmail) || StringUtils.isEmpty(loginPassword)) {
            throw new BadRequestException("적절하지 않은 요청입니다. (Please check the required value)");
        }
        if(!EmailUtility.checkEmailFormat(loginEmail)) {
            /**
             * [Refactoring]
             * 1. User 클래스에 checkEmailFormat 함수가 있는 것이 적절한지 ? EmailUtil 과 같은 클래스를 생성하는 것이 적절할지 ?
             * 2. User 클래스의 checkEmailFormat 함수를 사용하는 것이 적절한지 ?
             * */
            throw new BadRequestException("적절하지 않은 이메일 형식 입니다. (Please check the email)");
        }

        User user = userRepository.findUserByEmail(loginEmail).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다. (Can't find the user information)"));
        UserSalt userSalt = userSaltRepository.findUserSaltByUserId(user.getId()).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다. (Can't find the salt information)"));

        if( !this.checkPw(loginPassword, userSalt.getDigest()) ) {
            throw new LoginFailureException("로그인에 실패했습니다.");
        }

        String subject = "login";
        String audience = loginEmail;
        String role = user.getRole();

        String token = jwtProvider.generateToken(subject, audience, role);
        Cookie cookie = cookieProvider.generateCookie("jwt", token);

        return cookie;
    }

    public boolean checkPw(String password, String hashed) {

        return BCrypt.checkpw(password, hashed);
    }
}
