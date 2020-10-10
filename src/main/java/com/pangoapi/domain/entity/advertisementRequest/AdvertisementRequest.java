package com.pangoapi.domain.entity.advertisementRequest;

import com.pangoapi.domain.entity.advertisementType.AdvertisementType;
import com.pangoapi.domain.entity.user.User;
import com.pangoapi.domain.enums.AdvertisementReviewStatus;
import com.pangoapi.dto.advertisementRequest.CreateDtoAdvertisementRequest;
import com.pangoapi.dto.advertisementRequest.UpdateDtoAdvertisementRequest;
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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
public class AdvertisementRequest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String rowPosition;
    private String columnPosition;
    private String reviewer;
    private LocalDate startedDate;
    private LocalDate finishedDate;

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

    public void changeRowPosition(String rowPosition) { this.rowPosition = rowPosition; }

    public void changeColumnPosition(String columnPosition) { this.columnPosition = columnPosition; }

    public void changeReviewStatusToApproval() { this.reviewStatus = AdvertisementReviewStatus.APPROVAL; }

    public void changeReviewStatusToPending() { this.reviewStatus = AdvertisementReviewStatus.PENDING; }

    public void changeReviewStatusToNonApproval() { this.reviewStatus = AdvertisementReviewStatus.NON_APPROVAL; }

    public void changeReviewer(String reviewer) { this.reviewer = reviewer; }

    public void changeStartedDate(LocalDate startedDate) { this.startedDate = startedDate; }

    public void changeFinishedDate(LocalDate finishedDate) { this.finishedDate = finishedDate; }

    /**
     * @description
     * 사용자가 업데이트 요청할 수 있는 필드/메소드 입니다.
     * 해당 메소드에 포함되지 않은 ReviewStatus, Reviewer 등의 필드는 관리자가 업데이트 요청할 수 있는 필드입니다. 즉, 관리자 관련 필드입니다.
     * */
    public void changeAdvertisementRequest(UpdateDtoAdvertisementRequest updateDtoAdvertisementRequest) {
        if(updateDtoAdvertisementRequest.getTitle() != null) changeTitle(updateDtoAdvertisementRequest.getTitle());
        if(updateDtoAdvertisementRequest.getDetailDescription() != null) changeDetailDescription(updateDtoAdvertisementRequest.getDetailDescription());
        if(updateDtoAdvertisementRequest.getImagePath() != null) changeImagePath(updateDtoAdvertisementRequest.getImagePath());
        if(updateDtoAdvertisementRequest.getSiteUrl() != null) changeStieUrl(updateDtoAdvertisementRequest.getSiteUrl());
        if(updateDtoAdvertisementRequest.getRowPosition() != null) changeRowPosition(updateDtoAdvertisementRequest.getRowPosition());
        if(updateDtoAdvertisementRequest.getColumnPosition() != null) changeColumnPosition(updateDtoAdvertisementRequest.getColumnPosition());
        if(updateDtoAdvertisementRequest.getStartedDate() != null) {
            try {
                changeStartedDate(LocalDate.parse(updateDtoAdvertisementRequest.getStartedDate()));
            } catch (NullPointerException | DateTimeParseException e) { }
        }
        if(updateDtoAdvertisementRequest.getFinishedDate() != null) {
            try {
                changeFinishedDate(LocalDate.parse(updateDtoAdvertisementRequest.getFinishedDate()));
            } catch (NullPointerException | DateTimeParseException e) { }
        }
    }

    public static AdvertisementRequest createAdvertisementRequest(CreateDtoAdvertisementRequest createDtoAdvertisementRequest, User user, AdvertisementType advertisementType) {
        LocalDate startedDate = LocalDate.now();
        LocalDate finishedDate = LocalDate.now().plusMonths(1);
        try {
            startedDate = LocalDate.parse(createDtoAdvertisementRequest.getStartedDate());
        } catch (NullPointerException | DateTimeParseException e) { }
        try {
            finishedDate = LocalDate.parse(createDtoAdvertisementRequest.getStartedDate());
        } catch (NullPointerException | DateTimeParseException e) { }

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
                .startedDate(startedDate)
                .finishedDate(finishedDate)
                .build();
    }
}
