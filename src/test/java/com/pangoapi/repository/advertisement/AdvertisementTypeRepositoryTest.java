package com.pangoapi.repository.advertisement;

import com.pangoapi.domain.entity.advertisement.AdvertisementType;
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
class AdvertisementTypeRepositoryTest {

    @Autowired TestEntityManager entityManager;
    @Autowired AdvertisementTypeRepository advertisementTypeRepository;

    private Long advertisementTypeId = null;
    private String advertisementTypeType = "1";
    private Long advertisementTypeWidth = 300L;
    private Long advertisementTypeHeight = 300L;

    @Test
    public void whenFindByType_thenReturnAdvertisementType(){
        // Given
        AdvertisementType saveAdvertisementType = new AdvertisementType(advertisementTypeId, advertisementTypeType, advertisementTypeWidth, advertisementTypeHeight);
        entityManager.persist(saveAdvertisementType);
        entityManager.flush();

        // When
        AdvertisementType findAdvertisementType = advertisementTypeRepository.findByType(saveAdvertisementType.getType()).orElse(null);

        // Then
        assertThat(findAdvertisementType.getId()).isEqualTo(saveAdvertisementType.getId());
    }
}