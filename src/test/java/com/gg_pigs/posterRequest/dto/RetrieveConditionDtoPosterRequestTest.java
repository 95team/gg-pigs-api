package com.gg_pigs.posterRequest.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RetrieveConditionDtoPosterRequestTest {

    RetrieveConditionDtoPosterRequest retrieveConditionDtoPosterRequest = new RetrieveConditionDtoPosterRequest();

    @DisplayName("테스트: isUnlimitedIsTrue")
    @Test
    void Test_isUnlimitedIsTrue() {
        // Given // When
        retrieveConditionDtoPosterRequest.isUnlimitedIsTrue();

        // Then
        Assertions.assertThat(retrieveConditionDtoPosterRequest.isUnlimited()).isTrue();
    }

    @DisplayName("테스트: isUnlimitedIsFalse")
    @Test
    void Test_isUnlimitedIsFalse() {
        // Given // When
        retrieveConditionDtoPosterRequest.isUnlimitedIsFalse();

        // Then
        Assertions.assertThat(retrieveConditionDtoPosterRequest.isUnlimited()).isFalse();
    }

    @DisplayName("테스트: isFilteredDateIsTrue")
    @Test
    void Test_isFilteredDateIsTrue() {
        // Given // When
        retrieveConditionDtoPosterRequest.isFilteredDateIsTrue();

        // Then
        Assertions.assertThat(retrieveConditionDtoPosterRequest.isFilteredDate()).isTrue();
    }

    @DisplayName("테스트: isFilteredDateIsFalse")
    @Test
    void Test_isFilteredDateIsFalse() {
        // Given // When
        retrieveConditionDtoPosterRequest.isFilteredDateIsFalse();

        // Then
        Assertions.assertThat(retrieveConditionDtoPosterRequest.isFilteredDate()).isFalse();
    }

    @DisplayName("테스트: hasUserEmailIsTrue")
    @Test
    void Test_hasUserEmailIsTrue() {
        // Given // When
        retrieveConditionDtoPosterRequest.hasUserEmailIsTrue();

        // Then
        Assertions.assertThat(retrieveConditionDtoPosterRequest.isHasUserEmail()).isTrue();
    }

    @DisplayName("테스트: hasUserEmailIsFalse")
    @Test
    void Test_hasUserEmailIsFalse() {
        // Given // When
        retrieveConditionDtoPosterRequest.hasUserEmailIsFalse();

        // Then
        Assertions.assertThat(retrieveConditionDtoPosterRequest.isHasUserEmail()).isFalse();
    }

    @DisplayName("테스트: pageIsDefault")
    @Test
    void Test_pageIsDefault() {
        // Given
        String defaultPage = "1";

        // When
        retrieveConditionDtoPosterRequest.pageIsDefault();

        // Then
        Assertions.assertThat(retrieveConditionDtoPosterRequest.getPage()).isEqualTo(defaultPage);
    }

    @DisplayName("테스트: calculatePage")
    @Test
    void Test_calculatePage() {
        // Given
        String page = "2";
        Long expectedStartIndexOfPage = 7L;
        Long expectedLastIndexOfPage = 12L;

        // When
        retrieveConditionDtoPosterRequest.setPage(page);
        retrieveConditionDtoPosterRequest.calculatePage();

        // Then
        Assertions.assertThat(retrieveConditionDtoPosterRequest.getPage()).isEqualTo(page);
        Assertions.assertThat(retrieveConditionDtoPosterRequest.getStartIndexOfPage()).isEqualTo(expectedStartIndexOfPage);
        Assertions.assertThat(retrieveConditionDtoPosterRequest.getLastIndexOfPage()).isEqualTo(expectedLastIndexOfPage);
    }

    @DisplayName("테스트: calculatePage (with NumberFormatException)")
    @Test
    void Test_calculatePage_with_NumberFormatException() {
        // Given
        String page = "2s";
        String expectedMessage = "적절하지 않은 요청입니다. (Please check the page parameter)";

        // When
        retrieveConditionDtoPosterRequest.setPage(page);
        NumberFormatException exception = assertThrows(NumberFormatException.class, () -> retrieveConditionDtoPosterRequest.calculatePage());

        // Then
        Assertions.assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }
}