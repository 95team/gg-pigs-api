package com.gg_pigs.poster.dto;

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

@ExtendWith(MockitoExtension.class)
class CreateDtoPosterTest {

    @Mock User user;
    @Mock PosterType posterType;
    @Mock PosterRequest posterRequest;

    String userEmail = "pigs95team@gmail.com";
    String prTitle = "Poster-Request Title";

    @DisplayName("테스트: createByPR (user, posterType is not null)")
    @Test
    public void Test_createByPR() {
        // Given
        Mockito.when(posterRequest.getTitle()).thenReturn(prTitle);
        Mockito.when(posterRequest.getUser()).thenReturn(user);
        Mockito.when(posterRequest.getPosterType()).thenReturn(posterType);

        Mockito.when(user.getEmail()).thenReturn(userEmail);

        Mockito.when(posterType.getType()).thenReturn("R1");

        // When
        CreateDtoPoster createDtoPoster = CreateDtoPoster.createByPR(posterRequest);

        // Then
        Assertions.assertThat(createDtoPoster.getTitle()).isEqualTo(prTitle);
        Assertions.assertThat(createDtoPoster.getUserEmail()).isEqualTo(user.getEmail());
        Assertions.assertThat(createDtoPoster.getPosterType()).isEqualTo(posterType.getType());
    }

    @DisplayName("테스트: createByPR (user, posterType is null)")
    @Test
    public void Test_createByPR_with_null() {
        // Given
        Mockito.when(posterRequest.getTitle()).thenReturn(prTitle);
        Mockito.when(posterRequest.getUser()).thenReturn(null);
        Mockito.when(posterRequest.getPosterType()).thenReturn(null);

        // When
        CreateDtoPoster createDtoPoster = CreateDtoPoster.createByPR(posterRequest);

        // Then
        Assertions.assertThat(createDtoPoster.getTitle()).isEqualTo(prTitle);
        Assertions.assertThat(createDtoPoster.getUserEmail()).isNull();
        Assertions.assertThat(createDtoPoster.getPosterType()).isNull();
    }
}