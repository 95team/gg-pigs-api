package com.gg_pigs.posterRequest.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.gg_pigs._common.CommonDefinition.POSTER_LAYOUT_SIZE;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RetrieveConditionDtoPosterRequestTest {

    RetrieveConditionDtoPosterRequest condition = new RetrieveConditionDtoPosterRequest();

    @DisplayName("[테스트] isUnlimitedIsTrue()")
    @Test
    void Test_isUnlimitedIsTrue() {
        // Given // When
        condition.isUnlimitedIsTrue();

        // Then
        Assertions.assertThat(condition.isUnlimited()).isTrue();
    }

    @DisplayName("[테스트] isUnlimitedIsFalse()")
    @Test
    void Test_isUnlimitedIsFalse() {
        // Given // When
        condition.isUnlimitedIsFalse();

        // Then
        Assertions.assertThat(condition.isUnlimited()).isFalse();
    }

    @DisplayName("[테스트] isFilteredDateIsTrue()")
    @Test
    void Test_isFilteredDateIsTrue() {
        // Given // When
        condition.isFilteredDateIsTrue();

        // Then
        Assertions.assertThat(condition.isFilteredDate()).isTrue();
    }

    @DisplayName("[테스트] isFilteredDateIsFalse()")
    @Test
    void Test_isFilteredDateIsFalse() {
        // Given // When
        condition.isFilteredDateIsFalse();

        // Then
        Assertions.assertThat(condition.isFilteredDate()).isFalse();
    }

    @DisplayName("[테스트] hasUserEmailIsTrue()")
    @Test
    void Test_hasUserEmailIsTrue() {
        // Given // When
        condition.hasUserEmailIsTrue();

        // Then
        Assertions.assertThat(condition.isHasUserEmail()).isTrue();
    }

    @DisplayName("[테스트] hasUserEmailIsFalse()")
    @Test
    void Test_hasUserEmailIsFalse() {
        // Given // When
        condition.hasUserEmailIsFalse();

        // Then
        Assertions.assertThat(condition.isHasUserEmail()).isFalse();
    }

    @DisplayName("[테스트] setPageCondition() : 모든 페이지 조회")
    @Test
    void Test_setPageCondition_with_unlimited() {
        // Given
        String page = "-1";

        // When
        condition.setPageCondition(page);

        // Then
        Assertions.assertThat(condition.isUnlimited()).isTrue();
    }

    @DisplayName("[테스트] setPageCondition() : 특정 페이지 '-1' 미만 혹은 0 조회 시, Default 설정")
    @Test
    void Test_setPageCondition_with_limited_case1() {
        // Given
        String page = "-2";
        long expectedStartedIndexOfPage = 1;
        long expectedLastIndexOfPage = POSTER_LAYOUT_SIZE;

        // When
        condition.setPageCondition(page);

        // Then
        Assertions.assertThat(condition.isUnlimited()).isFalse();
        Assertions.assertThat(condition.getStartIndexOfPage()).isEqualTo(expectedStartedIndexOfPage);
        Assertions.assertThat(condition.getLastIndexOfPage()).isEqualTo(expectedLastIndexOfPage);
    }

    @DisplayName("[테스트] setPageCondition() : 특정 페이지 '2' 조회")
    @Test
    void Test_setPageCondition_with_limited_case2() {
        // Given
        String page = "2";
        long expectedStartedIndexOfPage = ((Long.parseLong(page) - 1) * POSTER_LAYOUT_SIZE) + 1;
        long expectedLastIndexOfPage = (Long.parseLong(page) * POSTER_LAYOUT_SIZE);

        // When
        condition.setPageCondition(page);

        // Then
        Assertions.assertThat(condition.isUnlimited()).isFalse();
        Assertions.assertThat(condition.getStartIndexOfPage()).isEqualTo(expectedStartedIndexOfPage);
        Assertions.assertThat(condition.getLastIndexOfPage()).isEqualTo(expectedLastIndexOfPage);
    }

    @DisplayName("[테스트] setPageCondition() : 페이지 값 없을 경우, Default 설정")
    @Test
    void Test_setPageCondition_with_no_page_value() {
        // Given
        String page = null;
        long expectedStartedIndexOfPage = 1;
        long expectedLastIndexOfPage = POSTER_LAYOUT_SIZE;

        // When
        condition.setPageCondition(page);

        // Then
        Assertions.assertThat(condition.isUnlimited()).isFalse();
        Assertions.assertThat(condition.getStartIndexOfPage()).isEqualTo(expectedStartedIndexOfPage);
        Assertions.assertThat(condition.getLastIndexOfPage()).isEqualTo(expectedLastIndexOfPage);
    }

    @DisplayName("[테스트] setPageCondition() : NumberFormatException 에러 발생 (Page 값 -> 숫자로 변환 실패)")
    @Test
    void Test_setPageCondition_with_NumberFormatException() {
        // Given
        String page = "1s";
        String expectedMessage = "적절하지 않은 요청입니다. (Please check the page parameter)";

        // When
        NumberFormatException exception = assertThrows(NumberFormatException.class, () -> condition.setPageCondition(page));

        // Then
        Assertions.assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @DisplayName("[테스트] setUserEmailCondition() : 정상적인 Email 값인 경우")
    @Test
    void Test_setUserEmailCondition() {
        // Given
        String userEmail = "pigs95team@gamil.com";

        // When
        condition.setUserEmailCondition(userEmail);

        // Then
        Assertions.assertThat(condition.isHasUserEmail()).isTrue();
        Assertions.assertThat(condition.getUserEmail()).isEqualTo(userEmail);
    }

    @DisplayName("[테스트] setUserEmailCondition() : Email 값 없을 경우 hasUserEmail = false")
    @Test
    void Test_setUserEmailCondition_with_no_email() {
        // Given
        String userEmail = null;

        // When
        condition.setUserEmailCondition(userEmail);

        // Then
        Assertions.assertThat(condition.isHasUserEmail()).isFalse();
        Assertions.assertThat(condition.getUserEmail()).isNull();
    }

    @DisplayName("[테스트] setDateCondition() : 정상적인 date 값(true, y)인 경우")
    @Test
    void Test_setDateCondition_with_y() {
        // Given
        String isFilteredByDate = "Y";

        // When
        condition.setDateCondition(isFilteredByDate);

        // Then
        Assertions.assertThat(condition.isFilteredDate()).isTrue();
    }

    @DisplayName("[테스트] setDateCondition() : 정상적인 date 값(true, y)인 경우")
    @Test
    void Test_setDateCondition_with_true() {
        // Given
        String isFilteredByDate = "true";

        // When
        condition.setDateCondition(isFilteredByDate);

        // Then
        Assertions.assertThat(condition.isFilteredDate()).isTrue();
    }

    @DisplayName("[테스트] setDateCondition() : 정상적인 date 값(true, y)이 아닌 경우")
    @Test
    void Test_setDateCondition_with_no_date() {
        // Given
        String isFilteredByDate = null;

        // When
        condition.setDateCondition(isFilteredByDate);

        // Then
        Assertions.assertThat(condition.isFilteredDate()).isFalse();
    }
}