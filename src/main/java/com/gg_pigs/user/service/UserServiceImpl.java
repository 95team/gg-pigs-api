package com.gg_pigs.user.service;

import com.gg_pigs.user.dto.CreateDtoUser;
import com.gg_pigs.user.dto.RetrieveDtoUser;
import com.gg_pigs.user.dto.UpdateDtoUser;
import com.gg_pigs.user.entity.User;
import com.gg_pigs.user.repository.UserRepository;
import com.gg_pigs.userSalt.service.UserSaltService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserSaltService userSaltService;

    /**
     * CREATE
     */
    @Override
    @Transactional
    public Long createUser(CreateDtoUser createDtoUser) {
        if(createDtoUser.getEmail() == null ||createDtoUser.getPassword() == null) {
            throw new IllegalArgumentException("적절하지 않은 요청입니다. (Please check the required value)");
        }
        if(!User.checkEmailFormat(createDtoUser.getEmail())) {
            throw new IllegalArgumentException("적절하지 않은 이메일 형식 입니다. (Please check the email)");
        }
        if(userRepository.countByEmail(createDtoUser.getEmail()) >= 1) {
            throw new DataIntegrityViolationException("이미 사용 중인 이메일입니다. (Please check the email.)");
        }

        User newUser;
        Long newUserId;
        try {
            newUser = userRepository.save(User.createUser(createDtoUser));
            userSaltService.create(newUser, createDtoUser.getPassword());
            newUserId = newUser.getId();
        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityViolationException("적절하지 않은 요청입니다. (Please check the data. This is usually related to SQL errors.)");
        }

        return newUserId;
    }

    /**
     * RETRIEVE
     */
    @Override
    public RetrieveDtoUser retrieveUser(Long _userId) {
        User user = userRepository.findById(_userId).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        return RetrieveDtoUser.createRetrieveDtoUser(user);
    }

    @Override
    public RetrieveDtoUser retrieveUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        return RetrieveDtoUser.createRetrieveDtoUser(user);
    }

    @Override
    public List<RetrieveDtoUser> retrieveAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> RetrieveDtoUser.createRetrieveDtoUser(user)).collect(Collectors.toList());
    }

    /**
     * UPDATE
     */
    @Override
    @Transactional
    public Long updateUser(Long _userId, UpdateDtoUser updateDtoUser) {
        User user = userRepository.findById(_userId).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        user.changeUser(updateDtoUser);

        return user.getId();
    }

    /**
     * DELETE
     */
    @Override
    public void deleteUser(Long _userId) {
        userRepository.deleteById(_userId);
    }
}
