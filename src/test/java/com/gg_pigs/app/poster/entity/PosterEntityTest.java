package com.gg_pigs.app.poster.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

class PosterEntityTest {

    Poster poster = new Poster();

    @DisplayName("테스트: changeTitle()")
    @Test
    void Test_changeTitle() {
        // Given
        String title = "This is a title";

        // When
        poster.changeTitle(title);

        // Then
        Assertions.assertThat(poster.getTitle()).isEqualTo(title);
    }

    @DisplayName("테스트: changeDescription()")
    @Test
    void Test_changeDescription() {
        // Given
        String description = "This is a description";

        // When
        poster.changeDescription(description);

        // Then
        Assertions.assertThat(poster.getDescription()).isEqualTo(description);
    }

    @DisplayName("테스트: changeKeywords()")
    @Test
    void Test_changeKeywords() {
        // Given
        String keywords = "This is a keywords";

        // When
        poster.changeKeywords(keywords);

        // Then
        Assertions.assertThat(poster.getKeywords()).isEqualTo(keywords);
    }

    @DisplayName("테스트: changeImagePath()")
    @Test
    void Test_changeImagePath() {
        // Given
        String imagePath = "This is a imagePath";

        // When
        poster.changeImagePath(imagePath);

        // Then
        Assertions.assertThat(poster.getImagePath()).isEqualTo(imagePath);
    }

    @DisplayName("테스트: changeSiteUrl()")
    @Test
    void Test_changeSiteUrl() {
        // Given
        String siteUrl = "This is a siteUrl";

        // When
        poster.changeSiteUrl(siteUrl);

        // Then
        Assertions.assertThat(poster.getSiteUrl()).isEqualTo(siteUrl);
    }

    @DisplayName("테스트: changeRowPosition()")
    @Test
    void Test_changeRowPosition() {
        // Given
        Long rowPosition = 1L;

        // When
        poster.changeRowPosition(rowPosition);

        // Then
        Assertions.assertThat(poster.getRowPosition()).isEqualTo(rowPosition);
    }

    @DisplayName("테스트: changeColumnPosition()")
    @Test
    void Test_changeColumnPosition() {
        // Given
        Long columnPosition = 1L;

        // When
        poster.changeColumnPosition(columnPosition);

        // Then
        Assertions.assertThat(poster.getColumnPosition()).isEqualTo(columnPosition);
    }

    @DisplayName("테스트: changeIsActivatedToActivated()")
    @Test
    void changeIsActivatedToActivated() {
        // Given
        String expectedIsActivated = "Y";

        // When
        poster.changeIsActivatedToActivated();

        // Then
        Assertions.assertThat(poster.getIsActivated()).isEqualTo(expectedIsActivated);
    }

    @DisplayName("테스트: changeIsActivatedToDeactivated()")
    @Test
    void changeIsActivatedToDeactivated() {
        // Given
        String expectedIsActivated = "N";

        // When
        poster.changeIsActivatedToDeactivated();

        // Then
        Assertions.assertThat(poster.getIsActivated()).isEqualTo(expectedIsActivated);
    }

    @DisplayName("테스트: changeReviewStatusToApproval()")
    @Test
    void changeReviewStatusToApproval() {
        // Given // When
        poster.changeReviewStatusToApproval();

        // Then
        Assertions.assertThat(poster.getReviewStatus()).isEqualTo(PosterReviewStatus.APPROVAL);
    }

    @DisplayName("테스트: changeReviewStatusToPending()")
    @Test
    void changeReviewStatusToPending() {
        // Given // When
        poster.changeReviewStatusToPending();

        // Then
        Assertions.assertThat(poster.getReviewStatus()).isEqualTo(PosterReviewStatus.PENDING);
    }

    @DisplayName("테스트: changeReviewStatusToNonApproval()")
    @Test
    void changeReviewStatusToNonApproval() {
        // Given // When
        poster.changeReviewStatusToNonApproval();

        // Then
        Assertions.assertThat(poster.getReviewStatus()).isEqualTo(PosterReviewStatus.NON_APPROVAL);
    }

    @DisplayName("테스트: changeStartedDate()")
    @Test
    void Test_changeStartedDate() {
        // Given
        LocalDate startedDate = LocalDate.now();

        // When
        poster.changeStartedDate(startedDate);

        // Then
        Assertions.assertThat(poster.getStartedDate()).isEqualTo(startedDate);
    }

    @DisplayName("테스트: changeFinishedDate()")
    @Test
    void Test_changeFinishedDate() {
        // Given
        LocalDate finishedDate = LocalDate.now();

        // When
        poster.changeFinishedDate(finishedDate);

        // Then
        Assertions.assertThat(poster.getFinishedDate()).isEqualTo(finishedDate);
    }

    @DisplayName("테스트: changePoster()")
    @Test
    void Test_changePoster() throws Exception {
        // Given
        String title = "This is a title";
        Long rowPosition = 1L;
        Long columnPosition = 1L;
        LocalDate startedDate = LocalDate.now();
        LocalDate finishedDate = LocalDate.now().plusMonths(1);

        Poster newPoster = Mockito.mock(Poster.class);
        Mockito.when(newPoster.getTitle()).thenReturn(title);
        Mockito.when(newPoster.getRowPosition()).thenReturn(rowPosition);
        Mockito.when(newPoster.getColumnPosition()).thenReturn(columnPosition);
        Mockito.when(newPoster.getStartedDate()).thenReturn(startedDate);
        Mockito.when(newPoster.getFinishedDate()).thenReturn(finishedDate);

        // When
        poster.changePoster(newPoster);

        // Then
        Assertions.assertThat(poster.getTitle()).isEqualTo(title);
        Assertions.assertThat(poster.getRowPosition()).isEqualTo(rowPosition);
        Assertions.assertThat(poster.getColumnPosition()).isEqualTo(columnPosition);
        Assertions.assertThat(poster.getStartedDate()).isEqualTo(startedDate);
        Assertions.assertThat(poster.getFinishedDate()).isEqualTo(finishedDate);
    }

    @DisplayName("테스트: isNew")
    @Test
    void Test_isNew() {
       // given
        Poster poster = Poster.builder().reviewStatus(PosterReviewStatus.NEW).build();

       // when
        boolean is = poster.isNew();

        // then
        Assertions.assertThat(is).isTrue();
    }

    @DisplayName("테스트: isApproval")
    @Test
    void Test_isApproval() {
        // given
        Poster poster = Poster.builder().reviewStatus(PosterReviewStatus.APPROVAL).build();

        // when
        boolean is = poster.isApproval();

        // then
        Assertions.assertThat(is).isTrue();
    }

    @DisplayName("테스트: isNonApproval")
    @Test
    void Test_isNonApproval() {
        // given
        Poster poster = Poster.builder().reviewStatus(PosterReviewStatus.NON_APPROVAL).build();

        // when
        boolean is = poster.isNonApproval();

        // then
        Assertions.assertThat(is).isTrue();
    }

    @DisplayName("테스트: isPending")
    @Test
    void Test_isPending() {
        // given
        Poster poster = Poster.builder().reviewStatus(PosterReviewStatus.PENDING).build();

        // when
        boolean is = poster.isPending();

        // then
        Assertions.assertThat(is).isTrue();
    }
}