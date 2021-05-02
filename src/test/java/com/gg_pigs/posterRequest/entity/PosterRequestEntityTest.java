package com.gg_pigs.posterRequest.entity;

import com.gg_pigs._common.enums.PosterReviewStatus;
import com.gg_pigs.posterRequest.dto.CreateDtoPosterRequest;
import com.gg_pigs.posterRequest.dto.UpdateDtoPosterRequest;
import com.gg_pigs.posterType.entity.PosterType;
import com.gg_pigs.user.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PosterRequestEntityTest {

    PosterRequest posterRequest = new PosterRequest();

    @DisplayName("테스트: changeTitle")
    @Test
    void Test_changeTitle() {
        // Given
        String title = "This is a title";

        // When
        posterRequest.changeTitle(title);

        // Then
        Assertions.assertThat(posterRequest.getTitle()).isEqualTo(title);
    }

    @DisplayName("테스트: changeDescription")
    @Test
    void Test_changeDescription() {
        // Given
        String description = "This is a description";

        // When
        posterRequest.changeDescription(description);

        // Then
        Assertions.assertThat(posterRequest.getDescription()).isEqualTo(description);
    }

    @DisplayName("테스트: changeKeywords")
    @Test
    void Test_changeKeywords() {
        // Given
        String keywords = "This is a keywords";

        // When
        posterRequest.changeKeywords(keywords);

        // Then
        Assertions.assertThat(posterRequest.getKeywords()).isEqualTo(keywords);
    }

    @DisplayName("테스트: changeImagePath")
    @Test
    void Test_changeImagePath() {
        // Given
        String imagePath = "This is a imagePath";

        // When
        posterRequest.changeImagePath(imagePath);

        // Then
        Assertions.assertThat(posterRequest.getImagePath()).isEqualTo(imagePath);
    }

    @DisplayName("테스트: changeSiteUrl")
    @Test
    void Test_changeSiteUrl() {
        // Given
        String siteUrl = "This is a siteUrl";

        // When
        posterRequest.changeSiteUrl(siteUrl);

        // Then
        Assertions.assertThat(posterRequest.getSiteUrl()).isEqualTo(siteUrl);
    }

    @DisplayName("테스트: changeRowPosition")
    @Test
    void Test_changeRowPosition() {
        // Given
        Long rowPosition = 1L;

        // When
        posterRequest.changeRowPosition(rowPosition);

        // Then
        Assertions.assertThat(posterRequest.getRowPosition()).isEqualTo(rowPosition);
    }

    @DisplayName("테스트: changeColumnPosition")
    @Test
    void Test_changeColumnPosition() {
        // Given
        Long columnPosition = 1L;

        // When
        posterRequest.changeColumnPosition(columnPosition);

        // Then
        Assertions.assertThat(posterRequest.getColumnPosition()).isEqualTo(columnPosition);
    }

    @DisplayName("테스트: changeReviewStatusToApproval")
    @Test
    void Test_changeReviewStatusToApproval() {
        // Given // When
        posterRequest.changeReviewStatusToApproval();

        // Then
        Assertions.assertThat(posterRequest.getReviewStatus()).isEqualTo(PosterReviewStatus.APPROVAL);
    }

    @DisplayName("테스트: changeReviewStatusToPending")
    @Test
    void Test_changeReviewStatusToPending() {
        // Given // When
        posterRequest.changeReviewStatusToPending();

        // Then
        Assertions.assertThat(posterRequest.getReviewStatus()).isEqualTo(PosterReviewStatus.PENDING);
    }

    @DisplayName("테스트: changeReviewStatusToNonApproval")
    @Test
    void Test_changeReviewStatusToNonApproval() {
        // Given // When
        posterRequest.changeReviewStatusToNonApproval();

        // Then
        Assertions.assertThat(posterRequest.getReviewStatus()).isEqualTo(PosterReviewStatus.NON_APPROVAL);
    }

    @DisplayName("테스트: changeReviewer")
    @Test
    void Test_changeReviewer() {
        // Given
        String reviewer = "리뷰어";

        // When
        posterRequest.changeReviewer(reviewer);

        // Then
        Assertions.assertThat(posterRequest.getReviewer()).isEqualTo(reviewer);
    }

    @DisplayName("테스트: changeStartedDate")
    @Test
    void Test_changeStartedDate() {
        // Given
        LocalDate startedDate = LocalDate.now();

        // When
        posterRequest.changeStartedDate(startedDate);

        // Then
        Assertions.assertThat(posterRequest.getStartedDate()).isEqualTo(startedDate);
    }

    @DisplayName("테스트: changeFinishedDate")
    @Test
    void Test_changeFinishedDate() {
        // Given
        LocalDate finishedDate = LocalDate.now();

        // When
        posterRequest.changeFinishedDate(finishedDate);

        // Then
        Assertions.assertThat(posterRequest.getFinishedDate()).isEqualTo(finishedDate);
    }

    @DisplayName("테스트: changePosterRequest")
    @Test
    void Test_changePosterRequest() throws Exception {
        // Given
        String title = "This is a title";
        String rowPosition = "1";
        String columnPosition = "1";
        String startedDate = LocalDate.now().toString();
        String finishedDate = LocalDate.now().plusMonths(1).toString();

        UpdateDtoPosterRequest updateDtoPosterRequest = Mockito.mock(UpdateDtoPosterRequest.class);
        Mockito.when(updateDtoPosterRequest.getTitle()).thenReturn(title);
        Mockito.when(updateDtoPosterRequest.getRowPosition()).thenReturn(rowPosition);
        Mockito.when(updateDtoPosterRequest.getColumnPosition()).thenReturn(columnPosition);
        Mockito.when(updateDtoPosterRequest.getStartedDate()).thenReturn(startedDate);
        Mockito.when(updateDtoPosterRequest.getFinishedDate()).thenReturn(finishedDate);

        // When
        posterRequest.changePosterRequest(updateDtoPosterRequest);

        // Then
        Assertions.assertThat(posterRequest.getTitle()).isEqualTo(title);
        Assertions.assertThat(posterRequest.getRowPosition()).isEqualTo(Long.parseLong(rowPosition));
        Assertions.assertThat(posterRequest.getColumnPosition()).isEqualTo(Long.parseLong(columnPosition));
        Assertions.assertThat(posterRequest.getStartedDate()).isEqualTo(startedDate);
        Assertions.assertThat(posterRequest.getFinishedDate()).isEqualTo(finishedDate);
    }

    @DisplayName("테스트: changePosterRequest (with Exception, rowPosition can't be parsed to Long.class)")
    @Test
    void Test_changePosterRequest_with_rowPosition_Exception() {
        // Given
        String expectedMessage = "적절하지 않은 요청입니다. (Please check the parameters)";
        String rowPosition = "1s";

        UpdateDtoPosterRequest updateDtoPosterRequest = Mockito.mock(UpdateDtoPosterRequest.class);
        Mockito.when(updateDtoPosterRequest.getRowPosition()).thenReturn(rowPosition);

        // When
        Exception exception = assertThrows(Exception.class, () -> posterRequest.changePosterRequest(updateDtoPosterRequest));

        // Then
        Assertions.assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @DisplayName("테스트: changePosterRequest (with Exception, columnPosition can't be parsed to Long.class)")
    @Test
    void Test_changePosterRequest_with_columnPosition_Exception() {
        // Given
        String expectedMessage = "적절하지 않은 요청입니다. (Please check the parameters)";
        String columnPosition = "1s";

        UpdateDtoPosterRequest updateDtoPosterRequest = Mockito.mock(UpdateDtoPosterRequest.class);
        Mockito.when(updateDtoPosterRequest.getColumnPosition()).thenReturn(columnPosition);

        // When
        Exception exception = assertThrows(Exception.class, () -> posterRequest.changePosterRequest(updateDtoPosterRequest));

        // Then
        Assertions.assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @DisplayName("테스트: changePosterRequest (with IllegalArgumentException, startedDate can't be parsed to LocalDate.class)")
    @Test
    void Test_changePosterRequest_with_startedDate_IllegalArgumentException() {
        // Given
        String expectedMessage = "적절하지 않은 날짜형식 입니다. (Please check the data 'startedDate')";
        String startedDate = "2021-01-01-01";

        UpdateDtoPosterRequest updateDtoPosterRequest = Mockito.mock(UpdateDtoPosterRequest.class);
        Mockito.when(updateDtoPosterRequest.getStartedDate()).thenReturn(startedDate);

        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> posterRequest.changePosterRequest(updateDtoPosterRequest));

        // Then
        Assertions.assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @DisplayName("테스트: changePosterRequest (with IllegalArgumentException, finishedDate can't be parsed to LocalDate.class)")
    @Test
    void Test_changePosterRequest_with_finishedDate_IllegalArgumentException() {
        // Given
        String expectedMessage = "적절하지 않은 날짜형식 입니다. (Please check the data 'finishedDate')";
        String finishedDate = "2021-01-01-01";

        UpdateDtoPosterRequest updateDtoPosterRequest = Mockito.mock(UpdateDtoPosterRequest.class);
        Mockito.when(updateDtoPosterRequest.getFinishedDate()).thenReturn(finishedDate);

        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> posterRequest.changePosterRequest(updateDtoPosterRequest));

        // Then
        Assertions.assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @DisplayName("테스트: createPosterRequest")
    @Test
    void Test_createPosterRequest() throws Exception {
        // Given
        User user = Mockito.mock(User.class);
        PosterType posterType = Mockito.mock(PosterType.class);
        CreateDtoPosterRequest createDtoPosterRequest = Mockito.mock(CreateDtoPosterRequest.class);

        Mockito.when(createDtoPosterRequest.getRowPosition()).thenReturn("1");
        Mockito.when(createDtoPosterRequest.getColumnPosition()).thenReturn("1");
        Mockito.when(createDtoPosterRequest.getStartedDate()).thenReturn(LocalDate.now().toString());
        Mockito.when(createDtoPosterRequest.getFinishedDate()).thenReturn(LocalDate.now().plusMonths(1).toString());

        // When
        PosterRequest posterRequest = PosterRequest.createPosterRequest(createDtoPosterRequest, user, posterType);

        // Then
        Assertions.assertThat(posterRequest.getId()).isEqualTo(null);
        Assertions.assertThat(posterRequest.getReviewStatus()).isEqualTo(PosterReviewStatus.NEW);
        Assertions.assertThat(posterRequest.getReviewer()).isEqualTo(null);
    }

    @DisplayName("테스트: createPosterRequest (with Exception)")
    @Test
    void Test_createPosterRequest_with_Exception() throws Exception {
        // Given
        String expectedMessage = "적절하지 않은 요청입니다. (Please check the parameters)";

        User user = Mockito.mock(User.class);
        PosterType posterType = Mockito.mock(PosterType.class);
        CreateDtoPosterRequest createDtoPosterRequest = Mockito.mock(CreateDtoPosterRequest.class);

        Mockito.when(createDtoPosterRequest.getRowPosition()).thenReturn("1s");

        // When
        Exception exception = assertThrows(Exception.class, () -> PosterRequest.createPosterRequest(createDtoPosterRequest, user, posterType));

        // Then
        Assertions.assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }
}