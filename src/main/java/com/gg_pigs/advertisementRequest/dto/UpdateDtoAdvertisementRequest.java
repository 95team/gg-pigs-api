package com.gg_pigs.advertisementRequest.dto;

import lombok.Getter;

@Getter
public class UpdateDtoAdvertisementRequest {

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
    private Boolean reviewStatus;
    private String startedDate;
    private String finishedDate;
}
