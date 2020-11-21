package com.pangoapi.user.repository;

import com.pangoapi.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired TestEntityManager entityManager;
    @Autowired UserRepository userRepository;

    Long defaultId = null;
    String defaultName = null;
    String defaultEmail = "pigs95team@gmail.com";
    String defaultPhone = null;
    String defaultRole = "ROLE_USER";
    String defaultOauthType = null;
    Character defaultIsActivated = 'N';
    Character defaultIsAuthenticated = 'N';

    @Test
    public void When_call_FindUserByEmail_Then_return_User() {
        // Given
        User savedUser = new User(defaultId, defaultName, defaultEmail, defaultPhone, defaultRole, defaultOauthType, defaultIsActivated, defaultIsAuthenticated);
        entityManager.persist(savedUser);
        entityManager.flush();

        // When
        User findUser = userRepository.findUserByEmail(defaultEmail).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        // Then
        assertThat(findUser.getId()).isEqualTo(savedUser.getId());
    }

    @Test
    public void When_call_countByEmail_Then_return_count() {
        // Given
        User savedUser = new User(defaultId, defaultName, defaultEmail, defaultPhone, defaultRole, defaultOauthType, defaultIsActivated, defaultIsAuthenticated);
        entityManager.persist(savedUser);
        entityManager.flush();;

        // When
        Long numberOfEmailsInUse = userRepository.countByEmail(defaultEmail);

        // Then
        assertThat(numberOfEmailsInUse).isEqualTo(1L);
    }
}