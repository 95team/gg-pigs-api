package com.pangoapi.user.service;

import com.pangoapi.user.dto.CreateDtoUser;
import com.pangoapi.user.dto.RetrieveDtoUser;
import com.pangoapi.user.dto.UpdateDtoUser;
import com.pangoapi.user.entity.User;
import com.pangoapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * CREATE
     */
    @Transactional
    public Long createOneUser(CreateDtoUser createDtoUser) {
        Long userId = userRepository.save(User.createUser(createDtoUser)).getId();

        return userId;
    }

    /**
     * RETRIEVE
     */
    public RetrieveDtoUser retrieveOneUser(Long _userId) {
        User user = userRepository.findById(_userId).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        return RetrieveDtoUser.createRetrieveDtoUser(user);
    }

    public List<RetrieveDtoUser> retrieveAllUser() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> RetrieveDtoUser.createRetrieveDtoUser(user)).collect(Collectors.toList());
    }

    /**
     * UPDATE
     */
    @Transactional
    public Long updateOneUser(Long _userId, UpdateDtoUser updateDtoUser) {
        User user = userRepository.findById(_userId).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        user.changeUser(updateDtoUser);

        return user.getId();
    }

    /**
     * DELETE
     */
    public void deleteOneUser(Long _userId) {
        userRepository.deleteById(_userId);
    }
}
