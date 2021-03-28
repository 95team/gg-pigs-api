package com.gg_pigs.poster.service;

import com.gg_pigs.poster.entity.Poster;
import com.gg_pigs.posterType.entity.PosterType;
import com.gg_pigs.user.entity.User;
import com.gg_pigs.poster.dto.CreateDtoPoster;
import com.gg_pigs.poster.dto.RetrieveDtoPoster;
import com.gg_pigs.poster.dto.UpdateDtoPoster;
import com.gg_pigs.poster.repository.PosterRepository;
import com.gg_pigs.posterType.repository.PosterTypeRepository;
import com.gg_pigs.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

/**
 * [References]
 * 1. https://www.baeldung.com/spring-boot-testing#mocking-with-mockbean
 * 2. https://brunch.co.kr/@springboot/207
 * */

@SpringBootTest(
        classes = {
                PosterServiceImpl.class,
        }
)
class PosterServiceImplTest {

    @Autowired PosterServiceImpl posterService;

    @MockBean UserRepository userRepository;
    @MockBean PosterTypeRepository posterTypeRepository;
    @MockBean PosterRepository posterRepository;

    @Mock User savedUser;
    @Mock PosterType savedPosterType;
    @Mock Poster savedPoster;

    private Long posterId = 1L;
    private Long rowPosition = 1L;
    private Long columnPosition = 1L;
    private String title = "This is a title";
    private String posterType = "R1";

    @BeforeEach
    void setUp() {
        // Configuration of poster
        Mockito.when(savedPoster.getId()).thenReturn(posterId);
        Mockito.when(savedPoster.getTitle()).thenReturn(title);
        Mockito.when(savedPoster.getPosterType()).thenReturn(savedPosterType);
        Mockito.when(savedPoster.getStartedDate()).thenReturn(LocalDate.now());
        Mockito.when(savedPoster.getFinishedDate()).thenReturn(LocalDate.now().plusMonths(1));

        // Configuration of userRepository
        Mockito.when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.ofNullable(savedUser));

        // Configuration of posterRepository
        Mockito.when(posterRepository.save(any(Poster.class))).thenReturn(savedPoster);
        Mockito.when(posterRepository.findById(anyLong())).thenReturn(Optional.ofNullable(savedPoster));

        // Configuration of posterTypeRepository
        Mockito.when(posterTypeRepository.findPosterTypeByType(anyString())).thenReturn(Optional.of(savedPosterType));
    }

    @Test
    public void When_call_createPoster_Then_return_savedPosterId() throws Exception {
        // Given
        CreateDtoPoster createDtoPoster = Mockito.mock(CreateDtoPoster.class);
        Mockito.when(createDtoPoster.getPosterType()).thenReturn(posterType);
        Mockito.when(createDtoPoster.getRowPosition()).thenReturn(String.valueOf(rowPosition));
        Mockito.when(createDtoPoster.getColumnPosition()).thenReturn(String.valueOf(columnPosition));
        Mockito.when(createDtoPoster.getStartedDate()).thenReturn(String.valueOf(LocalDate.now()));
        Mockito.when(createDtoPoster.getFinishedDate()).thenReturn(String.valueOf(LocalDate.now().plusMonths(1)));

        // When
        Long savedPosterId = posterService.createPoster(createDtoPoster);

        // Then
        assertThat(savedPosterId).isEqualTo(posterId);  // 1. createOnePoster 함수의 반환 값은 savedPosterId 이다.
        Mockito.verify(posterRepository, times(1)).save(any(Poster.class)); // 2. posterRepository 의 save 함수가 1회 호출된다.
    }

    @Test
    public void When_call_retrievePoster_Then_return_retrieveDtoPoster() {
        // Given
        Long savedPosterId = savedPoster.getId();

        // When
        RetrieveDtoPoster foundPoster = posterService.retrievePoster(savedPosterId);

        // Then
        assertThat(foundPoster.getClass()).isEqualTo(RetrieveDtoPoster.class);  // 1. retrieveOnePoster 함수의 반환 클래스는 RetrieveDtoPoster 클래스다.
        Mockito.verify(posterRepository, times(1)).findById(anyLong()); // 2. PosterRepository 의 findById 함수가 1회 호출된다.
    }

    @Test
    public void When_call_retrieveAllPosters_v2_Then_return_list() {
        // Given
        HashMap<String, String> retrieveCondition = new HashMap<>();
        retrieveCondition.put("page", null);
        retrieveCondition.put("userEmail", null);
        retrieveCondition.put("isFilteredDate", null);

        // When
        List<Poster> posterList = posterService.retrieveAllPosters(retrieveCondition);

        // Then
        assertThat(posterList.getClass()).isEqualTo(ArrayList.class);
        Mockito.verify(posterRepository, times(1)).findAllByCondition(any());
    }

    @Test
    public void When_call_updatePoster_Then_return_updatedPosterId() throws Exception {
        // Given
        UpdateDtoPoster updateDtoPoster = Mockito.mock(UpdateDtoPoster.class);
        Mockito.when(updateDtoPoster.getId()).thenReturn(posterId);

        // When
        Long updatedPosterId = posterService.updatePoster(updateDtoPoster.getId(), updateDtoPoster);

        // Then
        assertThat(updatedPosterId).isEqualTo(updateDtoPoster.getId()); // 1. updateOnePoster 함수의 반환 값은 updatedPosterId 이다.
        Mockito.verify(savedPoster, times(1)).changePoster(any(UpdateDtoPoster.class)); // 2. Poster 의 changePoster 메소드가 1회 호출된다.
        Mockito.verify(posterRepository, times(1)).findById(anyLong()); // 3. PosterRepository 의 findById 함수가 1회 호출된다.
    }

    @Test
    public void When_call_deletePoster_Then_return_void() {
        // Given // When
        posterService.deletePoster(savedPoster.getId());

        // Then
        Mockito.verify(posterRepository, times(1)).deleteById(any(Long.class));  // 1. posterRepository 의 deleteById 함수가 1회 호출된다.
    }
}