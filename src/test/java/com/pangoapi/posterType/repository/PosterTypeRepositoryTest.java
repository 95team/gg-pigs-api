package com.pangoapi.posterType.repository;

import com.pangoapi.posterType.entity.PosterType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * [References]
 * 1. https://www.baeldung.com/spring-boot-testing#integration-testing-with-datajpatest
 * */

@DataJpaTest
class PosterTypeRepositoryTest {

    @Autowired TestEntityManager entityManager;
    @Autowired PosterTypeRepository posterTypeRepository;

    @Test
    public void When_call_FindPosterTypeByType_Then_return_PosterType(){
        // Given
        PosterType savedPosterType = new PosterType(null, "R1", "300", "250");
        entityManager.persist(savedPosterType);
        entityManager.flush();

        // When
        PosterType foundPosterType = posterTypeRepository.findPosterTypeByType(savedPosterType.getType()).orElse(null);

        // Then
        assertThat(foundPosterType.getId()).isEqualTo(savedPosterType.getId());
    }
}