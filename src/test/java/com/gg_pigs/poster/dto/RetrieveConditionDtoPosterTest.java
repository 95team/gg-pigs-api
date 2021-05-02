package com.gg_pigs.poster.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RetrieveConditionDtoPosterTest {

    RetrieveConditionDtoPoster retrieveConditionDtoPoster = new RetrieveConditionDtoPoster();

    @BeforeEach
    void setUp() {

    }

    @DisplayName("테스트: isUnlimitedIsTrue")
    @Test
    void Test_isUnlimitedIsTrue() {
        // Given // When
        retrieveConditionDtoPoster.isUnlimitedIsTrue();

        // Then
        Assertions.assertThat(retrieveConditionDtoPoster.isUnlimited()).isTrue();
    }

    @DisplayName("테스트: isUnlimitedIsFalse")
    @Test
    void Test_isUnlimitedIsFalse() {
        // Given // When
        retrieveConditionDtoPoster.isUnlimitedIsFalse();

        // Then
        Assertions.assertThat(retrieveConditionDtoPoster.isUnlimited()).isFalse();
    }

    @DisplayName("테스트: isFilteredDateIsTrue")
    @Test
    void Test_isFilteredDateIsTrue() {
        // Given // When
        retrieveConditionDtoPoster.isFilteredDateIsTrue();

        // Then
        Assertions.assertThat(retrieveConditionDtoPoster.isFilteredDate()).isTrue();
    }

    @DisplayName("테스트: isFilteredDateIsFalse")
    @Test
    void Test_isFilteredDateIsFalse() {
        // Given // When
        retrieveConditionDtoPoster.isFilteredDateIsFalse();

        // Then
        Assertions.assertThat(retrieveConditionDtoPoster.isFilteredDate()).isFalse();
    }

    @DisplayName("테스트: hasUserEmailIsTrue")
    @Test
    void Test_hasUserEmailIsTrue() {
        // Given // When
        retrieveConditionDtoPoster.hasUserEmailIsTrue();

        // Then
        Assertions.assertThat(retrieveConditionDtoPoster.isHasUserEmail()).isTrue();
    }

    @DisplayName("테스트: hasUserEmailIsFalse")
    @Test
    void Test_hasUserEmailIsFalse() {
        // Given // When
        retrieveConditionDtoPoster.hasUserEmailIsFalse();

        // Then
        Assertions.assertThat(retrieveConditionDtoPoster.isHasUserEmail()).isFalse();
    }

    @DisplayName("테스트: filteredByActivatedIsTrue")
    @Test
    void Test_filteredByActivatedIsTrue() {
        // Given // When
        retrieveConditionDtoPoster.filteredByActivatedIsTrue();

        // Then
        Assertions.assertThat(retrieveConditionDtoPoster.isFilteredByActivated()).isTrue();
    }

    @DisplayName("테스트: filteredByActivatedIsFalse")
    @Test
    void Test_filteredByActivatedIsFalse() {
        // Given // When
        retrieveConditionDtoPoster.filteredByActivatedIsFalse();

        // Then
        Assertions.assertThat(retrieveConditionDtoPoster.isFilteredByActivated()).isFalse();
    }

    @DisplayName("테스트: pageIsDefault")
    @Test
    void Test_pageIsDefault() {
        // Given
        String defaultPage = "1";

        // When
        retrieveConditionDtoPoster.pageIsDefault();

        // Then
        Assertions.assertThat(retrieveConditionDtoPoster.getPage()).isEqualTo(defaultPage);
    }

    @DisplayName("테스트: calculatePage")
    @Test
    void Test_calculatePage() {
        // Given
        String page = "2";
        Long expectedStartIndexOfPage = 7L;
        Long expectedLastIndexOfPage = 12L;

        // When
        retrieveConditionDtoPoster.setPage(page);
        retrieveConditionDtoPoster.calculatePage();

        // Then
        Assertions.assertThat(retrieveConditionDtoPoster.getPage()).isEqualTo(page);
        Assertions.assertThat(retrieveConditionDtoPoster.getStartIndexOfPage()).isEqualTo(expectedStartIndexOfPage);
        Assertions.assertThat(retrieveConditionDtoPoster.getLastIndexOfPage()).isEqualTo(expectedLastIndexOfPage);
    }

    @DisplayName("테스트: calculatePage (with NumberFormatException)")
    @Test
    void Test_calculatePage_with_NumberFormatException() {
        // Given
        String page = "2s";
        String expectedMessage = "적절하지 않은 요청입니다. (Please check the page parameter)";

        // When
        retrieveConditionDtoPoster.setPage(page);
        NumberFormatException exception = assertThrows(NumberFormatException.class, () -> retrieveConditionDtoPoster.calculatePage());

        // Then
        Assertions.assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }
}