package com.pangoapi.advertisementRequest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateDtoAdvertisementRequest {

    private String title;
    private String userEmail;
    private String detailDescription;
    private String advertisementType;
    private String imagePath;
    private String siteUrl;
    private String rowPosition;
    private String columnPosition;
    private String startedDate;
    private String finishedDate;
}
