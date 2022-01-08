package com.gg_pigs.app.poster.entity;

import com.gg_pigs.app.poster.dto.CreateDtoPoster;
import com.gg_pigs.app.poster.dto.UpdateDtoPoster;
import com.gg_pigs.app.posterType.entity.PosterType;
import com.gg_pigs.app.user.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
        char expectedIsActivated = 'Y';

        // When
        poster.changeIsActivatedToActivated();

        // Then
        Assertions.assertThat(poster.getIsActivated()).isEqualTo(expectedIsActivated);
    }

    @DisplayName("테스트: changeIsActivatedToDeactivated()")
    @Test
    void changeIsActivatedToDeactivated() {
        // Given
        char expectedIsActivated = 'N';

        // When
        poster.changeIsActivatedToDeactivated();

        // Then
        Assertions.assertThat(poster.getIsActivated()).isEqualTo(expectedIsActivated);
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
        String rowPosition = "1";
        String columnPosition = "1";
        String startedDate = LocalDate.now().toString();
        String finishedDate = LocalDate.now().plusMonths(1).toString();

        UpdateDtoPoster updateDtoPoster = Mockito.mock(UpdateDtoPoster.class);
        Mockito.when(updateDtoPoster.getTitle()).thenReturn(title);
        Mockito.when(updateDtoPoster.getRowPosition()).thenReturn(rowPosition);
        Mockito.when(updateDtoPoster.getColumnPosition()).thenReturn(columnPosition);
        Mockito.when(updateDtoPoster.getStartedDate()).thenReturn(startedDate);
        Mockito.when(updateDtoPoster.getFinishedDate()).thenReturn(finishedDate);

        // When
        poster.changePoster(updateDtoPoster);

        // Then
        Assertions.assertThat(poster.getTitle()).isEqualTo(title);
        Assertions.assertThat(poster.getRowPosition()).isEqualTo(Long.parseLong(rowPosition));
        Assertions.assertThat(poster.getColumnPosition()).isEqualTo(Long.parseLong(columnPosition));
        Assertions.assertThat(poster.getStartedDate()).isEqualTo(startedDate);
        Assertions.assertThat(poster.getFinishedDate()).isEqualTo(finishedDate);
    }

    @DisplayName("테스트: changePoster() : Exception 에러 발생 (rowPosition can't be parsed to Long.class)")
    @Test
    void Test_changePoster_with_rowPosition_Exception() {
        // Given
        String expectedMessage = "적절하지 않은 요청입니다. (Please check the parameters)";
        String rowPosition = "1s";

        UpdateDtoPoster updateDtoPoster = Mockito.mock(UpdateDtoPoster.class);
        Mockito.when(updateDtoPoster.getRowPosition()).thenReturn(rowPosition);

        // When
        Exception exception = assertThrows(Exception.class, () -> poster.changePoster(updateDtoPoster));

        // Then
        Assertions.assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @DisplayName("테스트: changePoster() : Exception 에러 발생 (columnPosition can't be parsed to Long.class)")
    @Test
    void Test_changePoster_with_columnPosition_Exception() {
        // Given
        String expectedMessage = "적절하지 않은 요청입니다. (Please check the parameters)";
        String columnPosition = "1s";

        UpdateDtoPoster updateDtoPoster = Mockito.mock(UpdateDtoPoster.class);
        Mockito.when(updateDtoPoster.getColumnPosition()).thenReturn(columnPosition);

        // When
        Exception exception = assertThrows(Exception.class, () -> poster.changePoster(updateDtoPoster));

        // Then
        Assertions.assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @DisplayName("테스트: changePoster() : IllegalArgumentException 에러 발생 (startedDate can't be parsed to LocalDate.class)")
    @Test
    void Test_changePoster_with_startedDate_IllegalArgumentException() {
        // Given
        String expectedMessage = "적절하지 않은 날짜형식 입니다. (Please check the data 'startedDate')";
        String startedDate = "2021-01-01-01";

        UpdateDtoPoster updateDtoPoster = Mockito.mock(UpdateDtoPoster.class);
        Mockito.when(updateDtoPoster.getStartedDate()).thenReturn(startedDate);

        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> poster.changePoster(updateDtoPoster));

        // Then
        Assertions.assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @DisplayName("테스트: changePoster() : IllegalArgumentException 에러 발생 (finishedDate can't be parsed to LocalDate.class)")
    @Test
    void Test_changePoster_with_finishedDate_IllegalArgumentException() {
        // Given
        String expectedMessage = "적절하지 않은 날짜형식 입니다. (Please check the data 'finishedDate')";
        String finishedDate = "2021-01-01-01";

        UpdateDtoPoster updateDtoPoster = Mockito.mock(UpdateDtoPoster.class);
        Mockito.when(updateDtoPoster.getFinishedDate()).thenReturn(finishedDate);

        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> poster.changePoster(updateDtoPoster));

        // Then
        Assertions.assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @DisplayName("테스트: createPoster()")
    @Test
    void Test_createPoster() throws Exception {
        // Given
        User user = Mockito.mock(User.class);
        PosterType posterType = Mockito.mock(PosterType.class);
        CreateDtoPoster createDtoPoster = Mockito.mock(CreateDtoPoster.class);

        Mockito.when(createDtoPoster.getRowPosition()).thenReturn("1");
        Mockito.when(createDtoPoster.getColumnPosition()).thenReturn("1");
        Mockito.when(createDtoPoster.getStartedDate()).thenReturn(LocalDate.now().toString());
        Mockito.when(createDtoPoster.getFinishedDate()).thenReturn(LocalDate.now().plusMonths(1).toString());

        // When
        Poster poster = Poster.createPoster(createDtoPoster, user, posterType);

        // Then
        Assertions.assertThat(poster.getId()).isEqualTo(null);
    }

    @DisplayName("테스트: createPoster() : Exception 에러 발생 (rowPosition can't be parsed to Long.class)")
    @Test
    void Test_createPoster_with_Exception() throws Exception {
        // Given
        String expectedMessage = "적절하지 않은 요청입니다. (Please check the parameters)";
        String rowPosition = "1s";

        User user = Mockito.mock(User.class);
        PosterType posterType = Mockito.mock(PosterType.class);
        CreateDtoPoster createDtoPoster = Mockito.mock(CreateDtoPoster.class);

        Mockito.when(createDtoPoster.getRowPosition()).thenReturn(rowPosition);

        // When
        Exception exception = assertThrows(Exception.class, () -> Poster.createPoster(createDtoPoster, user, posterType));

        // Then
        Assertions.assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }
}