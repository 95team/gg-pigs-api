package com.gg_pigs.advertisement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateDtoAdvertisement {

    private Long id;
    private String userEmail;
    private String title;
    private String detailDescription;
    private String keywords;
    private String advertisementType;
    private String imagePath;
    private String siteUrl;
    private String rowPosition;
    private String columnPosition;
    private char isActivated;
    private String startedDate;
    private String finishedDate;
}
