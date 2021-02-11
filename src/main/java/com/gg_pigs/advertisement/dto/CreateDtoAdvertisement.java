package com.gg_pigs.advertisement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateDtoAdvertisement {

    private String title;
    private String userEmail;
    private String detailDescription;
    private String keywords;
    private String advertisementType;
    private String imagePath;
    private String siteUrl;
    private String rowPosition;
    private String columnPosition;
    private String startedDate;
    private String finishedDate;
}
