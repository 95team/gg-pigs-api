package com.pangoapi.posterRequest.dto;

import com.pangoapi.posterRequest.entity.PosterRequest;
import com.pangoapi._common.enums.PosterReviewStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Builder
@Getter
public class RetrieveDtoPosterRequest {

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
    private PosterReviewStatus reviewStatus;
    private String startedDate;
    private String finishedDate;

    public static com.pangoapi.posterRequest.dto.RetrieveDtoPosterRequest createRetrieveDtoPosterRequest(PosterRequest posterRequest) {
        return RetrieveDtoPosterRequest.builder()
                .id(posterRequest.getId())
                .userEmail(posterRequest.getUser() != null ? posterRequest.getUser().getEmail() : "")
                .title(posterRequest.getTitle())
                .description(posterRequest.getDescription())
                .keywords(posterRequest.getKeywords())
                .posterType(posterRequest.getPosterType().getType())
                .posterWidth(posterRequest.getPosterType().getWidth())
                .posterHeight(posterRequest.getPosterType().getHeight())
                .imagePath(posterRequest.getImagePath())
                .siteUrl(posterRequest.getSiteUrl())
                .rowPosition(Long.toString(posterRequest.getRowPosition()))
                .columnPosition(Long.toString(posterRequest.getColumnPosition()))
                .reviewStatus(posterRequest.getReviewStatus())
                .startedDate(posterRequest.getStartedDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .finishedDate(posterRequest.getFinishedDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .build();
    }
}
