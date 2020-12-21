package com.pangoapi.advertisementRequest.repository;

import com.pangoapi.advertisementRequest.dto.CreateDtoAdvertisementRequest;
import com.pangoapi.advertisementRequest.dto.RetrieveConditionForAdvertisementRequest;
import com.pangoapi.advertisementRequest.entity.AdvertisementRequest;
import com.pangoapi.advertisementType.entity.AdvertisementType;
import com.pangoapi.user.dto.CreateDtoUser;
import com.pangoapi.user.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
public class AdvertisementRequestRepositoryTest {

    @Autowired TestEntityManager entityManager;
    @Autowired AdvertisementRequestRepository advertisementRequestRepository;

    @BeforeEach
    void setUp() throws Exception {
        /**
         * 1. Advertisement-Reuqest 더미 데이터 삽입
         *  - User, AdvertisementType 데이터 삽입 (or 조회)
         *  - Position(rowPosition, columnPosition): [1,1], [1,2], [1,3], [1,4], [1,5], [1,6], [1,7]
         * */

        int numberOfDummyData = 7;
        String userEmail = "userEmail";
        String title = "title";
        String detailDescription = "detailDescription";
        String advertisementR1Type = "R1";
        String imagePath = "imagePath";
        String siteUrl = "siteUrl";
        String startedDate = LocalDate.now().toString();
        String finishedDate = LocalDate.now().plusMonths(1).toString();
        Long rowPosition = 1L;
        Long columnPosition = 1L;

        User user = User.createUser(new CreateDtoUser(null, userEmail, null, null, null, null, null, null));
        AdvertisementType advertisementType = entityManager.find(AdvertisementType.class, 1L);

        entityManager.persist(user);


        for(int i = 1; i < numberOfDummyData; i++, columnPosition++) {
            entityManager.persist(AdvertisementRequest.createAdvertisementRequest(
                    new CreateDtoAdvertisementRequest(title, userEmail, detailDescription, advertisementR1Type, imagePath, siteUrl, Long.toString(rowPosition), Long.toString(columnPosition), startedDate, finishedDate),
                    user,
                    advertisementType
            ));
        }
        entityManager.persist(AdvertisementRequest.createAdvertisementRequest(
                new CreateDtoAdvertisementRequest(title, userEmail, detailDescription, advertisementR1Type, imagePath, siteUrl, Long.toString(rowPosition), Long.toString(columnPosition), startedDate, finishedDate),
                user,
                advertisementType
        ));

        entityManager.flush();
    }

    /**
     * Test: findAllByCondition()
     * */
    @Test
    void When_call_findAllByCondition_Then_return_list_by_condition() {
        // Given
        int sizeOfAdvertisementRequests = 1;
        RetrieveConditionForAdvertisementRequest condition = new RetrieveConditionForAdvertisementRequest();

        // 1. Page 정보를 설정합니다.
        condition.setPage("2");
        condition.calculatePage();
        condition.isFilteredDateIsFalse();

        // 2. UserEmail 정보를 설정합니다.
        condition.hasUserEmailIsTrue();
        condition.setUserEmail("userEmail");

        // 3. IsFilteredDate 정보를 설정합니다.
        condition.isFilteredDateIsTrue();

        // When
        List<AdvertisementRequest> advertisementRequests = advertisementRequestRepository.findAllByCondition(condition);

        // Then
        Assertions.assertThat(advertisementRequests.size()).isEqualTo(sizeOfAdvertisementRequests);
    }
}
