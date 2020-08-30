package com.pangoapi.dto.advertisement;

import com.pangoapi.domain.entity.advertisement.Advertisement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
public class RetrieveDtoAdvertisement {

    private Long id;
    private String userEmail;
    private String title;
    private String briefDescription;
    private String detailDescription;
    private String advertisementType;
    private Long advertisementWidth;
    private Long advertisementHeight;
    private String imagePath;
    private String siteUrl;
    private Long rowPosition;
    private Long columnPosition;
    private Boolean isActivated;

    public static RetrieveDtoAdvertisement createRetrieveDtoAdvertisement(Advertisement advertisement) {
        return RetrieveDtoAdvertisement.builder()
                .id(advertisement.getId())
                .userEmail(advertisement.getUser() != null ? advertisement.getUser().getEmail() : "")
                .title(advertisement.getTitle())
                .briefDescription(advertisement.getBriefDescription())
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
