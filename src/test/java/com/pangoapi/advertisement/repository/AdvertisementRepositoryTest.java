package com.pangoapi.advertisement.repository;

import com.pangoapi.advertisement.dto.CreateDtoAdvertisement;
import com.pangoapi.advertisement.entity.Advertisement;
import com.pangoapi.advertisementType.entity.AdvertisementType;
import com.pangoapi.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.pangoapi._common.CommonDefinition.ADVERTISEMENT_LAYOUT_SIZE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@DataJpaTest
class AdvertisementRepositoryTest {

    @Autowired TestEntityManager entityManager;
    @Autowired AdvertisementRepository advertisementRepository;

    User user;
    AdvertisementType advertisementType;

    private String title = "title";
    private String userEmail = "userEmail";
    private String detailDescription = "detailDescription";
    private String advertisementR1Type = "R1";
    private String imagePath = "imagePath";
    private String siteUrl = "siteUrl";
    private String rowPosition = "1";
    private String columnPosition = "1";
    private String startedDate = LocalDate.now().toString();
    private String finishedDate = LocalDate.now().plusMonths(1).toString();
    private Long startIndexOfPage = 1L;
    private Long lastIndexOfPage = Long.valueOf(ADVERTISEMENT_LAYOUT_SIZE);

    @BeforeEach
    void setUp() throws Exception {
        /**
         * Advertisement 삽입: [1,1], [1,2], [1,3], [1,4], [1,5], [1,6], [1,7]
         */
        for (Long columnPosition = 1L; columnPosition <= ADVERTISEMENT_LAYOUT_SIZE; columnPosition++) {
            entityManager.persist(Advertisement.createAdvertisement(
                    new CreateDtoAdvertisement(title, userEmail, detailDescription, advertisementR1Type, imagePath, siteUrl, rowPosition, Long.toString(columnPosition), startedDate, finishedDate),
                    user,
                    advertisementType)
            );
        }
        entityManager.persist(Advertisement.createAdvertisement(
                new CreateDtoAdvertisement(title, userEmail, detailDescription, advertisementR1Type, imagePath, siteUrl, rowPosition, Long.toString(ADVERTISEMENT_LAYOUT_SIZE + 1), startedDate, finishedDate),
                user,
                advertisementType));
        entityManager.flush();
    }

    @Test
    void When_call_findAllImpossibleSeats_Then_return_filled_seats() throws Exception {
        // Given // When
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
    void When_call_findAllByPage_Then_return_paging_list() throws Exception {
        // Given // When
        List<Advertisement> allAdvertisements = advertisementRepository.findAll();
        List<Advertisement> pagedAdvertisements = advertisementRepository.findAllByPage(startIndexOfPage, lastIndexOfPage);

        // Then
        assertThat(allAdvertisements.size()).isEqualTo(ADVERTISEMENT_LAYOUT_SIZE + 1);
        assertThat(pagedAdvertisements.size()).isEqualTo(ADVERTISEMENT_LAYOUT_SIZE);
    }
}