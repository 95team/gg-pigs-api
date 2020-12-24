package com.pangoapi.poster.dto;

import com.pangoapi.poster.entity.Poster;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;


@AllArgsConstructor
@Builder
@Getter
public class RetrieveDtoPoster {

    private Long id;
    private String userEmail;
    private String title;
    private String description;
    private String keywords;
    private String posterType;
    private String posterWidth;
    private String posterHeight;
    private String imagePath;
    private String siteUrl;
    private String rowPosition;
    private String columnPosition;
    private char isActivated;
    private String startedDate;
    private String finishedDate;

    public static RetrieveDtoPoster createRetrieveDtoPoster(Poster poster) {
        return RetrieveDtoPoster.builder()
                .id(poster.getId())
                .userEmail(poster.getUser() != null ? poster.getUser().getEmail() : "")
                .title(poster.getTitle())
                .description(poster.getDescription())
                .keywords(poster.getKeywords())
                .posterType(poster.getPosterType().getType())
                .posterWidth(poster.getPosterType().getWidth())
                .posterHeight(poster.getPosterType().getHeight())
                .imagePath(poster.getImagePath())
                .siteUrl(poster.getSiteUrl())
                .rowPosition(Long.toString(poster.getRowPosition()))
                .columnPosition(Long.toString(poster.getColumnPosition()))
                .isActivated(poster.getIsActivated())
                .startedDate(poster.getStartedDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .finishedDate(poster.getFinishedDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .build();
    }
}
