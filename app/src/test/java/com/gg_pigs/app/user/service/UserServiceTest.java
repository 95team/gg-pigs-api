package com.gg_pigs.app.user.service;

import com.gg_pigs.app.user.dto.CreateDtoUser;
import com.gg_pigs.app.user.dto.RetrieveDtoUser;
import com.gg_pigs.app.user.dto.UpdateDtoUser;
import com.gg_pigs.app.user.entity.User;
import com.gg_pigs.app.user.entity.UserRole;
import com.gg_pigs.app.user.repository.UserRepository;
import com.gg_pigs.app.userSalt.service.UserSaltService;
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
        classes = { UserService.class }
)
class UserServiceTest {

    @Autowired
    UserService userService;

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
        userService.create(createDtoUser);

        // Then
        Mockito.verify(userRepository, Mockito.times(1)).countByEmail(anyString());
        Mockito.verify(userRepository, Mockito.times(1)).save(any(User.class));
    }

    @Test
    public void When_call_retrieveOneUser_Then_then_return_RetrieveDtoUser() {
        // Given
        Mockito.when(user.getRole()).thenReturn(UserRole.ROLE_USER);
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // When // Then
        assertThat(userService.read(userId).getClass()).isEqualTo(RetrieveDtoUser.class);
    }

    @Test
    public void When_call_retrieveAllUser_Then_return_List() {
        // Given // When // Then
        assertThat(userService.retrieveAllUsers().getClass()).isEqualTo(ArrayList.class);
    }

    @Test
    public void When_call_updateOneUser_Then_return_userId() {
        // Given
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // When // Then
        assertThat(userService.update(userId, updateDtoUser).getClass()).isEqualTo(Long.class);
    }

    @Test
    public void When_call_deleteOneUser_Then_call_deleteById_function() {
        // Given // When
        userService.delete(userId);

        // Then
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(anyLong());
    }
}