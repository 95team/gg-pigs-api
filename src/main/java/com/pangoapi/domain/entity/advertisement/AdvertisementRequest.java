package com.pangoapi.domain.entity.advertisement;

import com.pangoapi.domain.entity.user.User;
import com.pangoapi.domain.enums.AdvertisementReviewStatus;
import com.pangoapi.dto.advertisement.CreateDtoAdvertisement;
import com.pangoapi.dto.advertisement.CreateDtoAdvertisementRequest;
import com.pangoapi.dto.advertisement.UpdateDtoAdvertisementRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class AdvertisementRequest {

    @Id @GeneratedValue
    @Column(name = "ADVERTISEMENT_REQUEST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADVERTISEMENT_TYPE_ID")
    private AdvertisementType advertisementType;

    @Enumerated(EnumType.STRING)
    @Column(name = "REVIEW_STATUS")
    private AdvertisementReviewStatus reviewStatus;

    private String title;
    private String detailDescription;
    private String imagePath;
    private String siteUrl;
    private Long rowPosition;
    private Long columnPosition;
    private String reviewer;

    public void changeTitle(String title) {
        this.title = title;
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

    public void changeReviewStatusToApproval() { this.reviewStatus = AdvertisementReviewStatus.APPROVAL; }

    public void changeReviewStatusToPending() { this.reviewStatus = AdvertisementReviewStatus.PENDING; }

    public void changeReviewStatusToNonApproval() { this.reviewStatus = AdvertisementReviewStatus.NON_APPROVAL; }

    public void changeReviewer(String reviewer) { this.reviewer = reviewer; }

    /**
     * @name changeAdvertisementRequest
     * @description
     * 사용자가 업데이트 요청할 수 있는 필드/메소드 입니다.
     * ReviewStatus, Reviewer 필드는 관리자가 업데이트 요청할 수 있는 필드입니다. 즉, 관리자와 관련 필드입니다.
     * */
    public void changeAdvertisementRequest(UpdateDtoAdvertisementRequest updateDtoAdvertisementRequest) {
        if(updateDtoAdvertisementRequest.getTitle() != null) changeTitle(updateDtoAdvertisementRequest.getTitle());
        if(updateDtoAdvertisementRequest.getDetailDescription() != null) changeDetailDescription(updateDtoAdvertisementRequest.getDetailDescription());
        if(updateDtoAdvertisementRequest.getImagePath() != null) changeImagePath(updateDtoAdvertisementRequest.getImagePath());
        if(updateDtoAdvertisementRequest.getSiteUrl() != null) changeStieUrl(updateDtoAdvertisementRequest.getSiteUrl());
        if(updateDtoAdvertisementRequest.getRowPosition() != null) changeRowPosition(updateDtoAdvertisementRequest.getRowPosition());
        if(updateDtoAdvertisementRequest.getColumnPosition() != null) changeColumnPosition(updateDtoAdvertisementRequest.getColumnPosition());
    }

    public static AdvertisementRequest createAdvertisementRequest(CreateDtoAdvertisementRequest createDtoAdvertisementRequest, User user, AdvertisementType advertisementType) {
        return AdvertisementRequest.builder()
                .id(null)
                .user(user)
                .advertisementType(advertisementType)
                .reviewStatus(AdvertisementReviewStatus.NON_APPROVAL)
                .title(createDtoAdvertisementRequest.getTitle())
                .detailDescription(createDtoAdvertisementRequest.getDetailDescription())
                .imagePath(createDtoAdvertisementRequest.getImagePath())
                .siteUrl(createDtoAdvertisementRequest.getSiteUrl())
                .rowPosition(createDtoAdvertisementRequest.getRowPosition())
                .columnPosition(createDtoAdvertisementRequest.getColumnPosition())
                .reviewer(null)
                .build();
    }
}
