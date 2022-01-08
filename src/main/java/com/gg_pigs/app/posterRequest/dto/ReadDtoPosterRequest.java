package com.gg_pigs.app.posterRequest.dto;

import com.gg_pigs.app.posterRequest.entity.PosterRequest;
import com.gg_pigs.app.poster.entity.PosterReviewStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ReadDtoPosterRequest {

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

    public static ReadDtoPosterRequest of(PosterRequest posterRequest) {
        return ReadDtoPosterRequest.builder()
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
