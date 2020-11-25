package com.pangoapi.userSalt.service;

import com.pangoapi.user.entity.User;
import com.pangoapi.userSalt.entity.UserSalt;
import com.pangoapi.userSalt.repository.UserSaltRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserSaltService {

    private final UserSaltRepository userSaltRepository;

    /**
     * CREATE
     */
    @Transactional
    public Long createOneUserSalt(User user, String password) {
        String salt = UserSalt.generateSalt();
        String digest = UserSalt.generateDigest(password, salt);

        Long userSaltId;
        try {
            userSaltId = userSaltRepository.save(UserSalt.createUserSalt(salt, digest, user)).getId();
        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityViolationException("적절하지 않은 요청입니다. (Please check the data. This is usually related to SQL errors.)");
        }

        return userSaltId;
    }
}
