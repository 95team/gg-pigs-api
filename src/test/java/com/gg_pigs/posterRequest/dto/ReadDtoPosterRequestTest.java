package com.gg_pigs.posterRequest.dto;

import com.gg_pigs.posterRequest.entity.PosterRequest;
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

@ExtendWith(MockitoExtension.class)
class ReadDtoPosterRequestTest {

    @Mock User user;
    @Mock PosterRequest posterRequest;
    @Mock PosterType posterType;

    String uEmail = "pigs95team@gmail.com";
    String prTitle = "This is a title";
    String prType = "R1";

    LocalDate prStartedDate = LocalDate.now();
    LocalDate prFinishedDate = LocalDate.now().plusMonths(1);

    @BeforeEach
    void setUp() {
        Mockito.when(posterRequest.getTitle()).thenReturn(prTitle);
        Mockito.when(posterRequest.getPosterType()).thenReturn(posterType);
        Mockito.when(posterRequest.getStartedDate()).thenReturn(prStartedDate);
        Mockito.when(posterRequest.getFinishedDate()).thenReturn(prFinishedDate);

        Mockito.when(posterType.getType()).thenReturn(prType);
    }

    @DisplayName("[테스트] of()")
    @Test
    void Test_of() {
        // Given
        Mockito.when(posterRequest.getUser()).thenReturn(user);
        Mockito.when(user.getEmail()).thenReturn(uEmail);

        // When
        ReadDtoPosterRequest readDtoPosterRequest = ReadDtoPosterRequest.of(posterRequest);

        // Then
        Assertions.assertThat(readDtoPosterRequest.getTitle()).isEqualTo(prTitle);
        Assertions.assertThat(readDtoPosterRequest.getUserEmail()).isEqualTo(uEmail);
        Assertions.assertThat(readDtoPosterRequest.getPosterType()).isEqualTo(prType);
    }

    @DisplayName("[테스트] of() (user is null)")
    @Test
    void Test_of_with_null() {
        // Given // When
        ReadDtoPosterRequest readDtoPosterRequest = ReadDtoPosterRequest.of(posterRequest);

        // Then
        Assertions.assertThat(readDtoPosterRequest.getTitle()).isEqualTo(prTitle);
        Assertions.assertThat(readDtoPosterRequest.getUserEmail()).isEqualTo("");
        Assertions.assertThat(readDtoPosterRequest.getPosterType()).isEqualTo(prType);
    }
}