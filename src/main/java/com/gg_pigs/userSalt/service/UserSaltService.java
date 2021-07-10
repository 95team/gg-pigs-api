package com.gg_pigs.userSalt.service;

import com.gg_pigs.user.entity.User;
import com.gg_pigs.userSalt.dto.RetrieveDtoUserSalt;
import com.gg_pigs.userSalt.entity.UserSalt;
import com.gg_pigs.userSalt.repository.UserSaltRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserSaltService {

    private final UserSaltRepository userSaltRepository;

    /**
     * CREATE
     */
    @Transactional
    public Long create(User user, String password) {
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

    public RetrieveDtoUserSalt read(Long userId) {
        UserSalt userSalt = userSaltRepository.findUserSaltByUserId(userId).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        RetrieveDtoUserSalt retrieveDtoUserSalt = RetrieveDtoUserSalt.createRetrieveDtoUserSalt(userSalt);

        return retrieveDtoUserSalt;
    }
}
