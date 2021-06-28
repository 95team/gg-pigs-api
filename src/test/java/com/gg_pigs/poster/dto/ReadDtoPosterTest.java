package com.gg_pigs.poster.dto;

import com.gg_pigs.poster.entity.Poster;
import com.gg_pigs.posterType.entity.PosterType;
import com.gg_pigs.user.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static com.gg_pigs._common.CommonDefinition.ALLOWABLE_POSTER_SLUG_PATTERN_STRING;

@ExtendWith(MockitoExtension.class)
class ReadDtoPosterTest {

    @Mock User user;
    @Mock Poster poster;
    @Mock PosterType posterType;

    String uEmail = "pigs95team@gmail.com";
    String pTitle = "This is a poster title";
    String pType = "R1";
    LocalDate pStartedDate = LocalDate.now();
    LocalDate pFinishedDate = LocalDate.now().plusMonths(1);

    @BeforeEach
    void setUp() {
        Mockito.when(poster.getTitle()).thenReturn(pTitle);
        Mockito.when(poster.getPosterType()).thenReturn(posterType);
        Mockito.when(poster.getStartedDate()).thenReturn(pStartedDate);
        Mockito.when(poster.getFinishedDate()).thenReturn(pFinishedDate);
        Mockito.when(posterType.getType()).thenReturn(pType);
    }

    @DisplayName("[테스트] of() (user, posterType is not null)")
    @Test
    void Test_of() {
        // Given
        Mockito.when(poster.getUser()).thenReturn(user);
        Mockito.when(user.getEmail()).thenReturn(uEmail);

        // When
        ReadDtoPoster readDtoPoster = ReadDtoPoster.of(poster);

        // Then
        Assertions.assertThat(readDtoPoster.getTitle()).isEqualTo(pTitle);
        Assertions.assertThat(readDtoPoster.getUserEmail()).isEqualTo(uEmail);
        Assertions.assertThat(readDtoPoster.getPosterType()).isEqualTo(pType);
    }

    @DisplayName("[테스트] of() (user is null)")
    @Test
    void Test_of_with_null() {
        // Given // When
        ReadDtoPoster readDtoPoster = ReadDtoPoster.of(poster);

        // Then
        Assertions.assertThat(readDtoPoster.getPosterType()).isEqualTo(pType);
        Assertions.assertThat(readDtoPoster.getUserEmail()).isEqualTo("");
    }

    @DisplayName("[테스트] generateSlugByTitle()")
    @Test
    void Test_generateSlugByTitle() {
        // Given
        String title = "!@#$%^&*특수문자는 지워집니다. English is not erased. - 는 지워지지 않습니다.";
        Mockito.when(poster.getTitle()).thenReturn(title);

        // When
        ReadDtoPoster readDtoPoster = ReadDtoPoster.of(poster);

        // Then
        Assertions.assertThat(readDtoPoster.getSlug().replaceAll(ALLOWABLE_POSTER_SLUG_PATTERN_STRING, "")).isEqualTo(readDtoPoster.getSlug());
    }
}