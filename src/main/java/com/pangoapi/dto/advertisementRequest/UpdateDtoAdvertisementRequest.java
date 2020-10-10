package com.pangoapi.dto.advertisementRequest;

import lombok.Getter;

@Getter
public class UpdateDtoAdvertisementRequest {

    private Long id;
    private String userEmail;
    private String title;
    private String detailDescription;
    private String advertisementType;
    private String imagePath;
    private String siteUrl;
    private String rowPosition;
    private String columnPosition;
    private Boolean reviewStatus;
    private String startedDate;
    private String finishedDate;
}
