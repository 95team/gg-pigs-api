package com.pangoapi.dto.advertisement;

import com.pangoapi.domain.entity.advertisement.AdvertisementRequest;
import com.pangoapi.domain.enums.AdvertisementReviewStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RetrieveDtoAdvertisementRequest {

    private Long id;
    private String userEmail;
    private String title;
    private String detailDescription;
    private String advertisementType;
    private Long advertisementWidth;
    private Long advertisementHeight;
    private String imagePath;
    private String siteUrl;
    private Long rowPosition;
    private Long columnPosition;
    private AdvertisementReviewStatus reviewStatus;

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
                .build();
    }
}
