package com.pangoapi.advertisementRequest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static com.pangoapi._common.CommonDefinition.ADVERTISEMENT_LAYOUT_SIZE;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RetrieveConditionForAdvertisementRequest {

    String page;
    String userEmail;

    long startIndexOfPage = 1L;
    long lastIndexOfPage = startIndexOfPage * ADVERTISEMENT_LAYOUT_SIZE;

    LocalDate currentDate = LocalDate.now();

    boolean isUnlimited = false;
    boolean isFilteredDate = false;
    boolean hasUserEmail = false;

    public void isUnlimitedIsTrue() { this.isUnlimited = true; }

    public void isUnlimitedIsFalse() { this.isUnlimited = false; }

    public void isFilteredDateIsTrue() { this.isFilteredDate = true; }

    public void isFilteredDateIsFalse() { this.isFilteredDate = false; }

    public void hasUserEmailIsTrue() { this.hasUserEmail = true; }

    public void hasUserEmailIsFalse() { this.hasUserEmail = false; }

    public void pageIsDefault() { this.page = "1"; }

    public void calculatePage() {
        try {
            startIndexOfPage = ((Long.parseLong(page) - 1) * ADVERTISEMENT_LAYOUT_SIZE) + 1;
            lastIndexOfPage = (Long.parseLong(page) * ADVERTISEMENT_LAYOUT_SIZE);
        } catch (NumberFormatException exception) {
            throw new NumberFormatException("적절하지 않은 요청입니다. (Please check the page parameter)");
        }
    }
}