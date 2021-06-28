package com.gg_pigs.posterRequest.dto;

import com.gg_pigs.posterRequest.entity.PosterRequest;
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
class ReadDtoPosterRequestTest {

    @Mock User user;
    @Mock PosterRequest posterRequest;
    @Mock PosterType posterType;

    String uEmail = "pigs95team@gmail.com";
    String prTitle = "This is a title";
    String prType = "R1";

    LocalDate prStartedDate = LocalDate.now();
    LocalDate prFinishedDate = LocalDate.now().plusMonths(1);

    @DisplayName("테스트: createRetrieveDtoPosterRequest")
    @Test
    void Test_createRetrieveDtoPosterRequest() {
        // Given
        Mockito.when(posterRequest.getTitle()).thenReturn(prTitle);
        Mockito.when(posterRequest.getStartedDate()).thenReturn(prStartedDate);
        Mockito.when(posterRequest.getFinishedDate()).thenReturn(prFinishedDate);

        Mockito.when(posterRequest.getUser()).thenReturn(user);
        Mockito.when(posterRequest.getPosterType()).thenReturn(posterType);

        Mockito.when(user.getEmail()).thenReturn(uEmail);
        Mockito.when(posterType.getType()).thenReturn(prType);

        // When
        RetrieveDtoPosterRequest retrieveDtoPosterRequest = RetrieveDtoPosterRequest.createRetrieveDtoPosterRequest(posterRequest);

        // Then
        Assertions.assertThat(retrieveDtoPosterRequest.getTitle()).isEqualTo(prTitle);
        Assertions.assertThat(retrieveDtoPosterRequest.getUserEmail()).isEqualTo(uEmail);
        Assertions.assertThat(retrieveDtoPosterRequest.getPosterType()).isEqualTo(prType);
    }

    @DisplayName("테스트: createRetrieveDtoPosterRequest (user is null)")
    @Test
    void Test_createRetrieveDtoPosterRequest_with_null() {
        // Given
        Mockito.when(posterRequest.getTitle()).thenReturn(prTitle);
        Mockito.when(posterRequest.getStartedDate()).thenReturn(prStartedDate);
        Mockito.when(posterRequest.getFinishedDate()).thenReturn(prFinishedDate);

        Mockito.when(posterRequest.getPosterType()).thenReturn(posterType);

        Mockito.when(posterType.getType()).thenReturn(prType);

        // When
        RetrieveDtoPosterRequest retrieveDtoPosterRequest = RetrieveDtoPosterRequest.createRetrieveDtoPosterRequest(posterRequest);

        // Then
        Assertions.assertThat(retrieveDtoPosterRequest.getTitle()).isEqualTo(prTitle);
        Assertions.assertThat(retrieveDtoPosterRequest.getUserEmail()).isEqualTo("");
        Assertions.assertThat(retrieveDtoPosterRequest.getPosterType()).isEqualTo(prType);
    }
}