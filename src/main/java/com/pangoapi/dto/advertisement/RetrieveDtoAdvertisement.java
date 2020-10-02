package com.pangoapi.dto.advertisement;

import com.pangoapi.domain.entity.advertisement.Advertisement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@AllArgsConstructor
@Builder
@Getter
public class RetrieveDtoAdvertisement {

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
    private char isActivated;

    public static RetrieveDtoAdvertisement createRetrieveDtoAdvertisement(Advertisement advertisement) {
        return RetrieveDtoAdvertisement.builder()
                .id(advertisement.getId())
                .userEmail(advertisement.getUser() != null ? advertisement.getUser().getEmail() : "")
                .title(advertisement.getTitle())
                .detailDescription(advertisement.getDetailDescription())
                .advertisementType(advertisement.getAdvertisementType().getType())
                .advertisementWidth(advertisement.getAdvertisementType().getWidth())
                .advertisementHeight(advertisement.getAdvertisementType().getHeight())
                .imagePath(advertisement.getImagePath())
                .siteUrl(advertisement.getSiteUrl())
                .rowPosition(advertisement.getRowPosition())
                .columnPosition(advertisement.getColumnPosition())
                .isActivated(advertisement.getIsActivated())
                .build();
    }
}
