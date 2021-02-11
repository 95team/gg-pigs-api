package com.gg_pigs.posterRequest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
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
    private String reviewStatus;
    private String startedDate;
    private String finishedDate;
}
