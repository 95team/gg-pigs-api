package com.pangoapi.posterRequest.dto;

import lombok.Getter;

@Getter
public class UpdateDtoPosterRequest {

    private Long id;
    private String userEmail;
    private String title;
    private String description;
    private String keywords;
    private String posterType;
    private String imagePath;
    private String siteUrl;
    private String rowPosition;
    private String columnPosition;
    private Boolean reviewStatus;
    private String startedDate;
    private String finishedDate;
}
