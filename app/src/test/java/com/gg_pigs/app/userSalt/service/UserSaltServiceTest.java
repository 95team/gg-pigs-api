package com.gg_pigs.app.userSalt.service;

import com.gg_pigs.app.user.entity.User;
import com.gg_pigs.app.userSalt.dto.RetrieveDtoUserSalt;
import com.gg_pigs.app.userSalt.entity.UserSalt;
import com.gg_pigs.app.userSalt.repository.UserSaltRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest(
        classes = { UserSaltService.class }
)
class UserSaltServiceTest {

    @Autowired
    UserSaltService userSaltService;

    @MockBean UserSaltRepository userSaltRepository;

    @Mock User user;
    @Mock UserSalt userSalt;

    private String password = "thisisapassword";

    @Test
    public void Check_generateSalt_and_generateDigest_and_checkpw_works_well() {
        // Given
        String password1 = "thisisapassword1";
        String password2 = "thisisapassword2";
        String salt = UserSalt.generateSalt();
        String digest = UserSalt.generateDigest(password1, salt);

        // When
        boolean successResult = BCrypt.checkpw(password1, digest);
        boolean failureResult = BCrypt.checkpw(password2, digest);

        // Then
        assertThat(successResult).isEqualTo(true);
        assertThat(failureResult).isEqualTo(false);
    }

    @Test
    public void When_call_createUserSalt_Then_call_save_function() {
        // Given
        Mockito.when(userSaltRepository.save(any(UserSalt.class))).thenReturn(userSalt);
        Mockito.when(userSalt.getId()).thenReturn(1L);

        // When
        Long userSaltId = userSaltService.create(user, password);

        // Then
        Mockito.verify(userSaltRepository, Mockito.times(1)).save(any(UserSalt.class));
    }

    @Test
    public void When_call_retrieveUserSalt_Then_return_retrieveDtoUserSalt() {
        // Given
        Mockito.when(userSaltRepository.findUserSaltByUserId(anyLong())).thenReturn(Optional.ofNullable(userSalt));

        // When
        RetrieveDtoUserSalt retrieveDtoUserSalt = userSaltService.read(1L);

        // Then
        assertThat(retrieveDtoUserSalt).isNotNull();
    }
}