package com.gg_pigs.posterRequest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import static com.gg_pigs._common.CommonDefinition.POSTER_LAYOUT_SIZE;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RetrieveConditionDtoPosterRequest {

    private final int DEFAULT_VALUE = 1;
    private final int UNLIMIT_VALUE = -1;

    private String userEmail;

    private long startIndexOfPage = 1L;
    private long lastIndexOfPage = startIndexOfPage * POSTER_LAYOUT_SIZE;

    private boolean isUnlimited = false;
    private boolean isFilteredDate = false;
    private boolean hasUserEmail = false;

    public void isUnlimitedIsTrue() { this.isUnlimited = true; }

    public void isUnlimitedIsFalse() { this.isUnlimited = false; }

    public void isFilteredDateIsTrue() { this.isFilteredDate = true; }

    public void isFilteredDateIsFalse() { this.isFilteredDate = false; }

    public void hasUserEmailIsTrue() { this.hasUserEmail = true; }

    public void hasUserEmailIsFalse() { this.hasUserEmail = false; }

    public void setPageCondition(String page) {
        if(StringUtils.hasText(page)) {
            try {
                this.setPageCondition(Long.parseLong(page));
            } catch (NumberFormatException exception) {
                throw new NumberFormatException("적절하지 않은 요청입니다. (Please check the page parameter)");
            }
        }
        else {
            this.setPageCondition(DEFAULT_VALUE);
        }
    }

    public void setPageCondition(long page) {
        if(page == UNLIMIT_VALUE) {
            this.isUnlimitedIsTrue();
        }
        else if(page >= DEFAULT_VALUE) {
            this.isUnlimitedIsFalse();
            this.calculatePage(page);
        }
        else {
            this.isUnlimitedIsFalse();
            this.calculatePageByDefault();
        }
    }

    public void setUserEmailCondition(String userEmail) {
        if(StringUtils.hasText(userEmail)) {
            this.hasUserEmailIsTrue();
            this.userEmail = userEmail;
        }
        else {
            this.hasUserEmailIsFalse();
        }
    }

    public void setDateCondition(String isFilteredByDate) {
        if(StringUtils.hasText(isFilteredByDate)
                && (isFilteredByDate.equalsIgnoreCase("true") || isFilteredByDate.equalsIgnoreCase("y"))) {
            this.isFilteredDateIsTrue();
        }
        else {
            this.isFilteredDateIsFalse();
        }
    }

    private void calculatePageByDefault() {
        this.calculatePage(DEFAULT_VALUE);
    }

    private void calculatePage(long page) {
        startIndexOfPage = ((page - 1) * POSTER_LAYOUT_SIZE) + 1;
        lastIndexOfPage = (page * POSTER_LAYOUT_SIZE);
    }
}
