package com.pangoapi.dto.advertisementRequest;

import com.pangoapi.domain.entity.advertisementRequest.AdvertisementRequest;
import com.pangoapi.domain.enums.AdvertisementReviewStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Builder
@Getter
public class RetrieveDtoAdvertisementRequest {

    private Long id;
    private String userEmail;
    private String title;
    private String detailDescription;
    private String advertisementType;
    private String advertisementWidth;
    private String advertisementHeight;
    private String imagePath;
    private String siteUrl;
    private String rowPosition;
    private String columnPosition;
    private AdvertisementReviewStatus reviewStatus;
    private String startedDate;
    private String finishedDate;

    public static RetrieveDtoAdvertisementRequest createRetrieveDtoAdvertisementRequest(AdvertisementRequest advertisementRequest) {
        return RetrieveDtoAdvertisementRequest.builder()
                .id(advertisementRequest.getId())
                .userEmail(advertisementRequest.getUser() != null ? advertisementRequest.getUser().getEmail() : "")
                .title(advertisementRequest.getTitle())
                .detailDescription(advertisementRequest.getDetailDescription())
                .advertisementType(advertisementRequest.getAdvertisementType().getType())
                .advertisementWidth(advertisementRequest.getAdvertisementType().getWidth())
                .advertisementHeight(advertisementRequest.getAdvertisementType().getHeight())
                .imagePath(advertisementRequest.getImagePath())
                .siteUrl(advertisementRequest.getSiteUrl())
                .rowPosition(advertisementRequest.getRowPosition())
                .columnPosition(advertisementRequest.getColumnPosition())
                .reviewStatus(advertisementRequest.getReviewStatus())
                .startedDate(advertisementRequest.getStartedDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .finishedDate(advertisementRequest.getFinishedDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .build();
    }
}
