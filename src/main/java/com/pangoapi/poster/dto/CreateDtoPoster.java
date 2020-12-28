package com.pangoapi.poster.dto;

import com.pangoapi.posterRequest.entity.PosterRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CreateDtoPoster {

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

    public static CreateDtoPoster createByPR(PosterRequest posterRequest) {
        String userEmail = (posterRequest.getUser() != null)
                ? posterRequest.getUser().getEmail()
                : null;
        String posterType = (posterRequest.getPosterType() != null)
                ? posterRequest.getPosterType().getType()
                : null;

        return CreateDtoPoster.builder()
                .title(posterRequest.getTitle())
                .userEmail(userEmail)
                .description(posterRequest.getDescription())
                .keywords(posterRequest.getKeywords())
                .posterType(posterType)
                .imagePath(posterRequest.getImagePath())
                .siteUrl(posterRequest.getSiteUrl())
                .rowPosition(String.valueOf(posterRequest.getRowPosition()))
                .columnPosition(String.valueOf(posterRequest.getColumnPosition()))
                .startedDate(String.valueOf(posterRequest.getStartedDate()))
                .finishedDate(String.valueOf(posterRequest.getFinishedDate()))
                .build();
    }
}
