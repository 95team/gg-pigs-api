package com.gg_pigs.app.user.entity;

import com.gg_pigs.app.user.dto.CreateDtoUser;
import com.gg_pigs.app.user.dto.UpdateDtoUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    User user = new User();

    String name = "name";
    String email = "pigs95team@gmail.com";
    String phone = "010-0000-0000";

    @DisplayName("[테스트] changeName()")
    @Test
    void Test_changeName() {
        // Given // When
        user.changeName(name);

        // Then
        Assertions.assertThat(user.getName()).isEqualTo(name);
    }

    @DisplayName("[테스트] changeEmail()")
    @Test
    void Test_changeEmail() {
        // Given // When
        user.changeEmail(email);

        // Then
        Assertions.assertThat(user.getEmail()).isEqualTo(email);
    }

    @DisplayName("[테스트] changePhone()")
    @Test
    void Test_changePhone() {
        // Given // When
        user.changePhone(phone);

        // Then
        Assertions.assertThat(user.getPhone()).isEqualTo(phone);
    }

    @DisplayName("[테스트] changeRole()")
    @Test
    void Test_changeRole() {
        // Given
        String role = UserRole.ROLE_USER.name();

        // When
        user.changeRole(role);

        // Then
        Assertions.assertThat(user.getRole()).isEqualTo(role);
    }

    @DisplayName("[테스트] changeOauthType()")
    @Test
    void Test_changeOauthType() {
        // Given
        String oauthType = "NONE";

        // When
        user.changeOauthType(oauthType);

        // Then
        Assertions.assertThat(user.getOauthType()).isEqualTo(oauthType);
    }

    @DisplayName("[테스트] changeActivated()")
    @Test
    void Test_changeActivated() {
        // Given
        char isActivated = 'Y';

        // When
        user.changeActivated(isActivated);

        // Then
        Assertions.assertThat(user.getIsActivated()).isEqualTo(isActivated);
    }

    @DisplayName("[테스트] changeAuthenticated()")
    @Test
    void Test_changeAuthenticated() {
        // Given
        char isAuthenticated = 'Y';

        // When
        user.changeAuthenticated(isAuthenticated);

        // Then
        Assertions.assertThat(user.getIsAuthenticated()).isEqualTo(isAuthenticated);
    }

    @DisplayName("[테스트] changeUser()")
    @Test
    void Test_changeUser() {
        // Given
        UpdateDtoUser updateDtoUser = Mockito.mock(UpdateDtoUser.class);
        Mockito.when(updateDtoUser.getName()).thenReturn(name);
        Mockito.when(updateDtoUser.getEmail()).thenReturn(email);
        Mockito.when(updateDtoUser.getPhone()).thenReturn(phone);

        // When
        user.changeUser(updateDtoUser);

        // Then
        Assertions.assertThat(user.getName()).isEqualTo(name);
        Assertions.assertThat(user.getEmail()).isEqualTo(email);
        Assertions.assertThat(user.getPhone()).isEqualTo(phone);
    }

    @DisplayName("[테스트] createUser()")
    @Test
    void Test_createUser() {
        // Given
        String defaultRole = UserRole.ROLE_USER.name();
        Character defaultIsActivated = 'N';
        Character defaultIsAuthenticated = 'N';

        CreateDtoUser createDtoUser = Mockito.mock(CreateDtoUser.class);
        Mockito.when(createDtoUser.getName()).thenReturn(name);
        Mockito.when(createDtoUser.getEmail()).thenReturn(email);
        Mockito.when(createDtoUser.getPhone()).thenReturn(null);

        // When
        User user = User.createUser(createDtoUser);

        // Then
        Assertions.assertThat(user.getName()).isEqualTo(name);
        Assertions.assertThat(user.getEmail()).isEqualTo(email);
        Assertions.assertThat(user.getPhone()).isNull();
        Assertions.assertThat(user.getRole()).isEqualTo(defaultRole);
        Assertions.assertThat(user.getIsActivated()).isEqualTo(defaultIsActivated);
        Assertions.assertThat(user.getIsAuthenticated()).isEqualTo(defaultIsAuthenticated);
    }

    @DisplayName("[테스트] createUser() : IllegalArgumentException 에러 발생 (email 필수 값 누락)")
    @Test
    void Test_createUser_with_IllegalArgumentException() {
        // Given
        String expectedMessage = "이메일은 필수값 입니다. (Please check the data 'email')";

        CreateDtoUser createDtoUser = Mockito.mock(CreateDtoUser.class);
        Mockito.when(createDtoUser.getName()).thenReturn(name);
        Mockito.when(createDtoUser.getPhone()).thenReturn(phone);

        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> User.createUser(createDtoUser));

        // Then
        Assertions.assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @DisplayName("[테스트] checkEmailFormat()")
    @Test
    void Test_checkEmailFormat() {
        // Given
        String email = "pigs95team@gamil.com";
        String wrongEmailCase1 = "example@@example.com";
        String wrongEmailCase2 = "example@example.comcom";
        String wrongEmailCase3 = "example@example..com";
        String wrongEmailCase4 = "example@example,com";

        // When
        Boolean checkResult = User.checkEmailFormat(email);
        Boolean wrongEmailResult1 = User.checkEmailFormat(wrongEmailCase1);
        Boolean wrongEmailResult2 = User.checkEmailFormat(wrongEmailCase2);
        Boolean wrongEmailResult3 = User.checkEmailFormat(wrongEmailCase3);
        Boolean wrongEmailResult4 = User.checkEmailFormat(wrongEmailCase4);

        // Then
        Assertions.assertThat(checkResult).isTrue();
        Assertions.assertThat(wrongEmailResult1).isFalse();
        Assertions.assertThat(wrongEmailResult2).isFalse();
        Assertions.assertThat(wrongEmailResult3).isFalse();
        Assertions.assertThat(wrongEmailResult4).isFalse();
    }
}