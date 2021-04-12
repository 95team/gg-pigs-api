package com.gg_pigs.user.service;

import com.gg_pigs.user.dto.CreateDtoUser;
import com.gg_pigs.user.dto.RetrieveDtoUser;
import com.gg_pigs.user.dto.UpdateDtoUser;
import com.gg_pigs.user.entity.User;
import com.gg_pigs.user.repository.UserRepository;
import com.gg_pigs.userSalt.service.UserSaltService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest(
        classes = { UserServiceImpl.class }
)
class UserServiceImplTest {

    @Autowired UserServiceImpl userServiceImpl;

    @MockBean UserRepository userRepository;
    @MockBean UserSaltService userSaltService;

    @Mock User user;
    @Mock CreateDtoUser createDtoUser;
    @Mock UpdateDtoUser updateDtoUser;

    private Long userId = 1L;
    private String userEmail = "pigs95team@gmail.com";
    private String userPassword = "thisisapassword";

    @Test
    public void When_call_createOneUser_Then_call_save_function() {
        // Given
        Mockito.when(createDtoUser.getEmail()).thenReturn(userEmail);
        Mockito.when(createDtoUser.getPassword()).thenReturn(userPassword);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        userServiceImpl.createUser(createDtoUser);

        // Then
        Mockito.verify(userRepository, Mockito.times(1)).countByEmail(anyString());
        Mockito.verify(userRepository, Mockito.times(1)).save(any(User.class));
    }

    @Test
    public void When_call_retrieveOneUser_Then_then_return_RetrieveDtoUser() {
        // Given
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // When // Then
        assertThat(userServiceImpl.retrieveUser(userId).getClass()).isEqualTo(RetrieveDtoUser.class);
    }

    @Test
    public void When_call_retrieveAllUser_Then_return_List() {
        // Given // When // Then
        assertThat(userServiceImpl.retrieveAllUsers().getClass()).isEqualTo(ArrayList.class);
    }

    @Test
    public void When_call_updateOneUser_Then_return_userId() {
        // Given
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // When // Then
        assertThat(userServiceImpl.updateUser(userId, updateDtoUser).getClass()).isEqualTo(Long.class);
    }

    @Test
    public void When_call_deleteOneUser_Then_call_deleteById_function() {
        // Given // When
        userServiceImpl.deleteUser(userId);

        // Then
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(anyLong());
    }
}