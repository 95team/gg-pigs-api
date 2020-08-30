package com.pangoapi.domain.entity.advertisement;

import com.pangoapi.dto.advertisement.CreateDtoAdvertisement;
import com.pangoapi.domain.entity.user.User;
import com.pangoapi.dto.advertisement.UpdateDtoAdvertisement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
public class Advertisement {

    @Id @GeneratedValue
    @Column(name = "ADVERTISEMENT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADVERTISEMENT_TYPE_ID")
    private AdvertisementType advertisementType;

    private String title;
    private String briefDescription;
    private String detailDescription;
    private String imagePath;
    private String siteUrl;
    private Long rowPosition;
    private Long columnPosition;
    private Boolean isActivated;

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public void changeDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    public void changeImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void changeStieUrl(String siteUrl) { this.siteUrl = siteUrl; }

    public void changeRowPosition(Long rowPosition) { this.rowPosition = rowPosition; }

    public void changeColumnPosition(Long columnPosition) { this.columnPosition = columnPosition; }

    public void changeIsActivatedToActivated() { this.isActivated = true; }

    public void changeIsActivatedToDeactivated() { this.isActivated = false; }

    public void changeAdvertisement(UpdateDtoAdvertisement updateDtoAdvertisement) {
        changeTitle(updateDtoAdvertisement.getTitle());
        changeBriefDescription(updateDtoAdvertisement.getBriefDescription());
        changeDetailDescription(updateDtoAdvertisement.getDetailDescription());
        changeImagePath(updateDtoAdvertisement.getImagePath());
        changeStieUrl(updateDtoAdvertisement.getSiteUrl());
        changeRowPosition(updateDtoAdvertisement.getRowPosition());
        changeColumnPosition(updateDtoAdvertisement.getColumnPosition());
    }

    public static Advertisement createAdvertisement(CreateDtoAdvertisement createDtoAdvertisement, User user, AdvertisementType advertisementType) {
        return Advertisement.builder()
                .id(null)
                .user(user)
                .advertisementType(advertisementType)
                .title(createDtoAdvertisement.getTitle())
                .briefDescription(createDtoAdvertisement.getBriefDescription())
                .detailDescription(createDtoAdvertisement.getDetailDescription())
                .imagePath(createDtoAdvertisement.getImagePath())
                .siteUrl(createDtoAdvertisement.getSiteUrl())
                .rowPosition(createDtoAdvertisement.getRowPosition())
                .columnPosition(createDtoAdvertisement.getColumnPosition())
                .isActivated(false)
                .build();
    }
}
