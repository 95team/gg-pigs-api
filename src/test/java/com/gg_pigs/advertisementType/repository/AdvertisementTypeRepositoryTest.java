package com.gg_pigs.advertisementType.repository;

import com.gg_pigs.advertisementType.entity.AdvertisementType;
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
    private String advertisementTypeType = "R1";
    private String advertisementTypeWidth = "300";
    private String advertisementTypeHeight = "300";

    @Test
    public void When_call_FindByType_Then_return_AdvertisementType(){
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