package com.gg_pigs.advertisement.repository;

import com.gg_pigs.advertisement.dto.CreateDtoAdvertisement;
import com.gg_pigs.advertisement.dto.RetrieveConditionForAdvertisement;
import com.gg_pigs.advertisement.entity.Advertisement;
import com.gg_pigs.advertisementType.entity.AdvertisementType;
import com.gg_pigs.user.dto.CreateDtoUser;
import com.gg_pigs.user.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.gg_pigs._common.CommonDefinition.ADVERTISEMENT_LAYOUT_SIZE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@DataJpaTest
class AdvertisementRepositoryTest {

    @Autowired TestEntityManager entityManager;
    @Autowired AdvertisementRepository advertisementRepository;

    private final String userEmail = "userEmail";
    private final String detailDescription = "detailDescription";
    private final String keywords = "keywords";
    private final String advertisementR1Type = "R1";
    private final String imagePath = "imagePath";
    private final String siteUrl = "siteUrl";
    private final String rowPosition = "1";
    private final String columnPosition = "1";
    private final String startedDate = LocalDate.now().toString();
    private final String finishedDate = LocalDate.now().plusMonths(1).toString();

    private final int numberOfDummyData = 7;

    @BeforeEach
    void setUp() throws Exception {
        /**
         * (Dummy data) Advertisement 삽입: [1,1], [1,2], [1,3], [1,4], [1,5], [1,6], [1,7]
         */

        User user = User.createUser(new CreateDtoUser(null, userEmail, null, null, null, null, null, null));
        AdvertisementType advertisementType = entityManager.find(AdvertisementType.class, 1L);

        entityManager.persist(user);

        Long columnPosition = 1L;
        String title = "title";
        for(int i = 1; i < numberOfDummyData; i++, columnPosition++) {
            entityManager.persist(Advertisement.createAdvertisement(
                    new CreateDtoAdvertisement(title, userEmail, detailDescription, keywords, advertisementR1Type, imagePath, siteUrl, rowPosition, Long.toString(columnPosition), startedDate, finishedDate),
                    user,
                    advertisementType)
            );
        }
        entityManager.persist(Advertisement.createAdvertisement(
                new CreateDtoAdvertisement(title, userEmail, detailDescription, keywords, advertisementR1Type, imagePath, siteUrl, rowPosition, Long.toString(columnPosition), startedDate, finishedDate),
                user,
                advertisementType));

        entityManager.flush();
    }

    @Test
    void When_call_save_with_DataIntegrityViolationException_Then_throw_exception() throws Exception {
        // Given
        String wrongLengthOfTitle = "It can be up to 32 characters. It can be up to 32 characters.";

        // When // Then
        try {
            advertisementRepository.save(Advertisement.createAdvertisement(
                    new CreateDtoAdvertisement(wrongLengthOfTitle, userEmail, detailDescription, keywords, advertisementR1Type, imagePath, siteUrl, rowPosition, Long.toString(ADVERTISEMENT_LAYOUT_SIZE + 1), startedDate, finishedDate),
                    null,
                    null));
        } catch (DataIntegrityViolationException exception) {
            return;
        }
        Assertions.fail("TEST FAILED: When_call_save_with_DataIntegrityViolationException_Then_throw_exception");
    }

    @Test
    void When_call_findAllImpossibleSeats_Then_return_filled_seats() {
        // Given
        Long startIndexOfPage = 1L;
        Long lastIndexOfPage = Long.valueOf(ADVERTISEMENT_LAYOUT_SIZE);

        // When
        List<Map<String, String>> impossibleSeats = advertisementRepository.findAllImpossibleSeats(startIndexOfPage, lastIndexOfPage, LocalDate.now(), LocalDate.now().plusMonths(1));

        // Then
        assertThat(impossibleSeats.size()).isEqualTo(ADVERTISEMENT_LAYOUT_SIZE);
        for (Map<String, String> impossibleSeat : impossibleSeats) {
            if(!Arrays.asList(new String[]{"1", "2", "3", "4", "5", "6"}).contains(String.valueOf(impossibleSeat.get("columnPosition")))) {
                fail("TEST FAILED: When_call_findAllImpossibleSeats_Then_return_filled_seats");
            }
        }
    }

    @Test
    void When_call_findAllByPage_Then_return_paging_list() {
        // Given
        Long startIndexOfPage = 1L;
        Long lastIndexOfPage = Long.valueOf(ADVERTISEMENT_LAYOUT_SIZE);

        // When
        List<Advertisement> allAdvertisements = advertisementRepository.findAll();
        List<Advertisement> pagedAdvertisements = advertisementRepository.findAllByPage(startIndexOfPage, lastIndexOfPage);

        // Then
        assertThat(allAdvertisements.size()).isEqualTo(numberOfDummyData);
        assertThat(pagedAdvertisements.size()).isEqualTo(numberOfDummyData - 1);
    }

    @Test
    void When_call_findAllByCondition_Then_return_list_by_condition() {
        // Given
        int sizeOfAdvertisements = 1;
        RetrieveConditionForAdvertisement condition = new RetrieveConditionForAdvertisement();

        // 1. Page 정보를 설정합니다.
        condition.isUnlimitedIsFalse();
        condition.setPage("2");
        condition.calculatePage();

        // 2. UserEmail 정보를 설정합니다.
        condition.hasUserEmailIsTrue();
        condition.setUserEmail(userEmail);

        // 3. IsFilteredDate 정보를 설정합니다.
        condition.isFilteredDateIsTrue();

        // When
        List<Advertisement> advertisements = advertisementRepository.findAllByCondition(condition);

        // Then
        assertThat(advertisements.size()).isEqualTo(sizeOfAdvertisements);
    }
}