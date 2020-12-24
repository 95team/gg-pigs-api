package com.pangoapi.posterRequest.repository;

import com.pangoapi.posterRequest.dto.CreateDtoPosterRequest;
import com.pangoapi.posterRequest.dto.RetrieveConditionDtoPosterRequest;
import com.pangoapi.posterRequest.entity.PosterRequest;
import com.pangoapi.posterType.entity.PosterType;
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
public class PosterRequestRepositoryTest {

    @Autowired TestEntityManager entityManager;
    @Autowired PosterRequestRepository posterRequestRepository;

    @BeforeEach
    void setUp() throws Exception {
        /**
         * 1. Poster-Reuqest 더미 데이터 삽입
         *  - User, PosterType 데이터 삽입 (or 조회)
         *  - Position(rowPosition, columnPosition): [1,1], [1,2], [1,3], [1,4], [1,5], [1,6], [1,7]
         * */

        int numberOfDummyData = 7;

        String title = "This is a title.";
        String description = "This is a description.";
        String keywords = "This is a keywords.";
        String posterR1Type = "R1";
        String imagePath = "This is a image path";
        String siteUrl = "This is a site-url";
        String userEmail = "test@email.com";
        String startedDate = LocalDate.now().toString();
        String finishedDate = LocalDate.now().plusMonths(1).toString();
        Long rowPosition = 1L;
        Long columnPosition = 1L;

        User user = User.createUser(new CreateDtoUser(null, userEmail, null, null, null, null, null, null));
        PosterType posterType = entityManager.find(PosterType.class, 1L);

        entityManager.persist(user);


        for(int i = 1; i < numberOfDummyData; i++, columnPosition++) {
            entityManager.persist(PosterRequest.createPosterRequest(
                    new CreateDtoPosterRequest(title, userEmail, description, keywords, posterR1Type, imagePath, siteUrl, Long.toString(rowPosition), Long.toString(columnPosition), startedDate, finishedDate),
                    user,
                    posterType
            ));
        }
        entityManager.persist(PosterRequest.createPosterRequest(
                new CreateDtoPosterRequest(title, userEmail, description, keywords, posterR1Type, imagePath, siteUrl, Long.toString(rowPosition), Long.toString(columnPosition), startedDate, finishedDate),
                user,
                posterType
        ));

        entityManager.flush();
    }

    /**
     * Test: findAllByCondition()
     * */
    @Test
    void When_call_findAllByCondition_Then_return_list_by_condition() {
        // Given
        int sizeOfPosterRequests = 1;
        RetrieveConditionDtoPosterRequest condition = new RetrieveConditionDtoPosterRequest();

        // 1. Page 정보를 설정합니다.
        condition.setPage("2");
        condition.calculatePage();
        condition.isFilteredDateIsFalse();

        // 2. UserEmail 정보를 설정합니다. (Must be "test@email.com")
        condition.hasUserEmailIsTrue();
        condition.setUserEmail("test@email.com");

        // 3. IsFilteredDate 정보를 설정합니다.
        condition.isFilteredDateIsTrue();

        // When
        List<PosterRequest> posterRequests = posterRequestRepository.findAllByCondition(condition);

        // Then
        Assertions.assertThat(posterRequests.size()).isEqualTo(sizeOfPosterRequests);
    }
}
