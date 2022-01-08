package com.gg_pigs.app.posterRequest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateDtoPosterRequest {

    private String title;
    private String userEmail;
    private String description;
    private String keywords;
    private String posterType;
    private String imagePath;
    private String siteUrl;
    private String rowPosition;
    private String columnPosition;
    private String startedDate;
    private String finishedDate;
}
