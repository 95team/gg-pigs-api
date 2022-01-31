package com.gg_pigs.app.user.repository;

import com.gg_pigs._common.UserGenerator;
import com.gg_pigs.app.user.entity.User;
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

    @Test
    public void When_call_FindUserByEmail_Then_return_User() {
        // Given
        User user = UserGenerator.getInstance(null);
        entityManager.persist(user);
        entityManager.flush();

        // When
        User findUser = userRepository.findUserByEmail(user.getEmail()).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        // Then
        assertThat(findUser.getId()).isEqualTo(user.getId());
    }

    @Test
    public void When_call_countByEmail_Then_return_count() {
        // Given
        User user = UserGenerator.getInstance(null);
        entityManager.persist(user);
        entityManager.flush();

        // When
        Long numberOfEmailsInUse = userRepository.countByEmail(user.getEmail());

        // Then
        assertThat(numberOfEmailsInUse).isEqualTo(1L);
    }
}