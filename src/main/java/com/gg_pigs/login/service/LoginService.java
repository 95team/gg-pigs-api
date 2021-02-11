package com.gg_pigs.login.service;

import com.gg_pigs._common.exception.BadRequestException;
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

import javax.persistence.EntityNotFoundException;

/**
 * [References]
 * 1. https://velog.io/@ljinsk3/JWT%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-Bearer%EB%B0%A9%EC%8B%9D%EC%9C%BC%EB%A1%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8%ED%95%98%EA%B8%B0
 * */

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LoginService {

    private final UserRepository userRepository;
    private final UserSaltRepository userSaltRepository;

    public LoginResult login(RequestDtoLogin requestDtoLogin) {
        String loginEmail = requestDtoLogin.getEmail();
        String loginPassword = requestDtoLogin.getPassword();

        if(loginEmail == null || loginPassword == null) {
            throw new BadRequestException("적절하지 않은 요청입니다. (Please check the required value)");
        }
        if(!User.checkEmailFormat(loginEmail)) {
            /**
             * [Refactoring]
             * 1. User 클래스에 checkEmailFormat 함수가 있는 것이 적절한지 ? EmailUtil 과 같은 클래스를 생성하는 것이 적절할지 ?
             * 2. User 클래스의 checkEmailFormat 함수를 사용하는 것이 적절한지 ?
             * */
            throw new BadRequestException("적절하지 않은 이메일 형식 입니다. (Please check the email)");
        }

        User user = userRepository.findUserByEmail(loginEmail).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다. (Can't find the user information)"));
        UserSalt userSalt = userSaltRepository.findUserSaltByUserId(user.getId()).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다. (Can't find the salt information)"));

        LoginResult loginResult = new LoginResult(user.getEmail(), user.getRole());

        if(this.checkPw(loginPassword, userSalt.getDigest())) {
            loginResult.successLogin();
        } else {
            loginResult.failedLogin();
        }

        return loginResult;
    }

    public boolean checkPw(String password, String hashed) {

        return BCrypt.checkpw(password, hashed);
    }
}
