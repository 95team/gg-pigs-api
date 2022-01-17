package com.gg_pigs.app.poster.repository;

import com.gg_pigs.app.poster.dto.PosterDto;
import com.gg_pigs.app.poster.entity.Poster;
import com.gg_pigs.app.posterType.entity.PosterType;
import com.gg_pigs.app.user.dto.CreateDtoUser;
import com.gg_pigs.app.user.entity.User;
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

import static com.gg_pigs.global.CommonDefinition.POSTER_LAYOUT_SIZE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@DataJpaTest
class PosterRepositoryTest {

    @Autowired TestEntityManager entityManager;
    @Autowired PosterRepository posterRepository;

    private final String userEmail = "userEmail";
    private final LocalDate startedDate = LocalDate.now();
    private final LocalDate finishedDate = LocalDate.now().plusMonths(1);

    private final int numberOfDummyData = 7;

    @BeforeEach
    void setUp() {
        /**
         * (Dummy data) Poster 삽입: [1,1], [1,2], [1,3], [1,4], [1,5], [1,6], [1,7]
         */

        User user = User.createUser(new CreateDtoUser(null, userEmail, null, null, null, null, null, null));
        PosterType posterType = entityManager.find(PosterType.class, 1L);

        entityManager.persist(user);

        Long columnPosition = 1L;
        for(int i = 1; i <= numberOfDummyData; i++, columnPosition++) {
            PosterDto.Create.RequestDto requestDto = PosterDto.Create.RequestDto.builder()
                    .startedDate(startedDate)
                    .finishedDate(finishedDate)
                    .columnPosition(columnPosition).build();
            entityManager.persist(requestDto.toEntity(user, posterType));
        }

        entityManager.flush();
    }

    @Test
    void DataIntegrityViolationException_by_title() {
        // Given
        String wrongLengthOfTitle = "It can be up to 128 characters. It can be up to 128 characters. It can be up to 128 characters. It can be up to 128 characters. It can be up to 128 characters. It can be up to 128 characters.";
        PosterDto.Create.RequestDto requestDto = PosterDto.Create.RequestDto.builder().title(wrongLengthOfTitle).build();

        // When // Then
        try {
            posterRepository.save(requestDto.toEntity(null, null));
        } catch (DataIntegrityViolationException exception) {
            return;
        }
        Assertions.fail("TEST FAILED: When_call_save_with_DataIntegrityViolationException_Then_throw_exception");
    }

    @Test
    void When_call_findAllImpossibleSeats_Then_return_filled_seats() {
        // Given
        Long startIndexOfPage = 1L;
        Long lastIndexOfPage = Long.valueOf(POSTER_LAYOUT_SIZE);

        // When
        List<Map<String, String>> impossibleSeats = posterRepository.findAllImpossibleSeats(startIndexOfPage, lastIndexOfPage, LocalDate.now(), LocalDate.now().plusMonths(1));

        // Then
        assertThat(impossibleSeats.size()).isEqualTo(POSTER_LAYOUT_SIZE);
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
        Long lastIndexOfPage = Long.valueOf(POSTER_LAYOUT_SIZE);

        // When
        List<Poster> allPosters = posterRepository.findAll();
        List<Poster> pagedPosters = posterRepository.findAllByPage(startIndexOfPage, lastIndexOfPage);

        // Then
        assertThat(allPosters.size()).isEqualTo(numberOfDummyData);
        assertThat(pagedPosters.size()).isEqualTo(numberOfDummyData - 1);
    }

    @Test
    void When_call_findAllByCondition_Then_return_list_by_condition() {
        // Given
        int expectedNumberOfPosters = 1;
        PosterDto.Read.SearchConditionDto condition = PosterDto.Read.SearchConditionDto.of("2", null, null, null);

        // When
        List<Poster> posters = posterRepository.findAllByCondition(condition);

        // Then
        assertThat(posters.size()).isEqualTo(expectedNumberOfPosters);
    }
}