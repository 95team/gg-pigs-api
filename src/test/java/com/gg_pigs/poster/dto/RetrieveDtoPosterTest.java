package com.gg_pigs.poster.dto;

import com.gg_pigs.poster.entity.Poster;
import com.gg_pigs.posterType.entity.PosterType;
import com.gg_pigs.user.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class RetrieveDtoPosterTest {

    @Mock User user;
    @Mock Poster poster;
    @Mock PosterType posterType;

    String uEmail = "pigs95team@gmail.com";
    String pTitle = "This is a poster title";
    String pType = "R1";
    LocalDate pStartedDate = LocalDate.now();
    LocalDate pFinishedDate = LocalDate.now().plusMonths(1);

    @DisplayName("테스트: createRetrieveDtoPoster (user, posterType is not null)")
    @Test
    void Test_createRetrieveDtoPoster() {
        // Given
        Mockito.when(poster.getTitle()).thenReturn(pTitle);
        Mockito.when(poster.getUser()).thenReturn(user);
        Mockito.when(poster.getPosterType()).thenReturn(posterType);
        Mockito.when(poster.getStartedDate()).thenReturn(pStartedDate);
        Mockito.when(poster.getFinishedDate()).thenReturn(pFinishedDate);

        Mockito.when(user.getEmail()).thenReturn(uEmail);

        Mockito.when(posterType.getType()).thenReturn(pType);

        // When
        RetrieveDtoPoster retrieveDtoPoster = RetrieveDtoPoster.createRetrieveDtoPoster(poster);

        // Then
        Assertions.assertThat(retrieveDtoPoster.getTitle()).isEqualTo(pTitle);
        Assertions.assertThat(retrieveDtoPoster.getUserEmail()).isEqualTo(uEmail);
        Assertions.assertThat(retrieveDtoPoster.getPosterType()).isEqualTo(pType);
    }

    @DisplayName("테스트: createRetrieveDtoPoster (user is null)")
    @Test
    void Test_createRetrieveDtoPoster_with_null() {
        // Given
        Mockito.when(poster.getTitle()).thenReturn(pTitle);
        Mockito.when(poster.getPosterType()).thenReturn(posterType);
        Mockito.when(poster.getStartedDate()).thenReturn(pStartedDate);
        Mockito.when(poster.getFinishedDate()).thenReturn(pFinishedDate);
        Mockito.when(posterType.getType()).thenReturn(pType);

        // When
        RetrieveDtoPoster retrieveDtoPoster = RetrieveDtoPoster.createRetrieveDtoPoster(poster);

        // Then
        Assertions.assertThat(retrieveDtoPoster.getPosterType()).isEqualTo(pType);
        Assertions.assertThat(retrieveDtoPoster.getUserEmail()).isEqualTo("");
    }
}