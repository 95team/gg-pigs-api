package com.gg_pigs.advertisementRequest.entity;

import com.gg_pigs.advertisementType.entity.AdvertisementType;
import com.gg_pigs.user.entity.User;
import com.gg_pigs._common.enums.AdvertisementReviewStatus;
import com.gg_pigs.advertisementRequest.dto.CreateDtoAdvertisementRequest;
import com.gg_pigs.advertisementRequest.dto.UpdateDtoAdvertisementRequest;
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
    @Column(name = "REVIEW_STATUS", length = 32)
    private AdvertisementReviewStatus reviewStatus;

    @Column(length = 32)
    private String title;

    @Column(length = 128)
    private String detailDescription;

    @Column(length = 128)
    private String keywords;

    @Column(length = 128)
    private String imagePath;

    @Column(length = 255)
    private String siteUrl;

    @Column(length = 32)
    private String reviewer;

    private Long rowPosition;
    private Long columnPosition;
    private LocalDate startedDate;
    private LocalDate finishedDate;

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    public void changeKeywords(String keywords) {
        this.keywords = keywords;
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

    public void changeStartedDate(LocalDate startedDate) { this.startedDate = startedDate; }

    public void changeFinishedDate(LocalDate finishedDate) { this.finishedDate = finishedDate; }

    /**
     * @description
     * 사용자가 업데이트 요청할 수 있는 필드/메소드 입니다.
     * 해당 메소드에 포함되지 않은 ReviewStatus, Reviewer 등의 필드는 관리자가 업데이트 요청할 수 있는 필드입니다. 즉, 관리자 관련 필드입니다.
     * */
    public void changeAdvertisementRequest(UpdateDtoAdvertisementRequest updateDtoAdvertisementRequest) throws Exception {
        if(updateDtoAdvertisementRequest.getTitle() != null) changeTitle(updateDtoAdvertisementRequest.getTitle());
        if(updateDtoAdvertisementRequest.getDetailDescription() != null) changeDetailDescription(updateDtoAdvertisementRequest.getDetailDescription());
        if(updateDtoAdvertisementRequest.getKeywords() != null) changeKeywords(updateDtoAdvertisementRequest.getKeywords());
        if(updateDtoAdvertisementRequest.getImagePath() != null) changeImagePath(updateDtoAdvertisementRequest.getImagePath());
        if(updateDtoAdvertisementRequest.getSiteUrl() != null) changeStieUrl(updateDtoAdvertisementRequest.getSiteUrl());
        if(updateDtoAdvertisementRequest.getRowPosition() != null) {
            try {
                changeRowPosition(Long.parseLong(updateDtoAdvertisementRequest.getRowPosition()));
            } catch (Exception exception) {
                throw new Exception("적절하지 않은 요청입니다. (Please check the parameters)");
            }
        }
        if(updateDtoAdvertisementRequest.getColumnPosition() != null) {
            try {
                changeColumnPosition(Long.parseLong(updateDtoAdvertisementRequest.getColumnPosition()));
            } catch (Exception exception) {
                throw new Exception("적절하지 않은 요청입니다. (Please check the parameters)");
            }
        }
        if(updateDtoAdvertisementRequest.getStartedDate() != null) {
            try {
                changeStartedDate(LocalDate.parse(updateDtoAdvertisementRequest.getStartedDate()));
            } catch (NullPointerException | DateTimeParseException exception) {
                throw new IllegalArgumentException("적절하지 않은 날짜형식 입니다. (Please check the data 'startedDate')");
            }
        }
        if(updateDtoAdvertisementRequest.getFinishedDate() != null) {
            try {
                changeFinishedDate(LocalDate.parse(updateDtoAdvertisementRequest.getFinishedDate()));
            } catch (NullPointerException | DateTimeParseException exception) {
                throw new IllegalArgumentException("적절하지 않은 날짜형식 입니다. (Please check the data 'finishedDate')");
            }
        }
    }

    public static AdvertisementRequest createAdvertisementRequest(CreateDtoAdvertisementRequest createDtoAdvertisementRequest, User user, AdvertisementType advertisementType) throws Exception {
        LocalDate startedDate = LocalDate.now(); LocalDate finishedDate = LocalDate.now().plusMonths(1);
        Long rowPosition, columnPosition;
        try {
            rowPosition = Long.parseLong(createDtoAdvertisementRequest.getRowPosition());
            columnPosition = Long.parseLong(createDtoAdvertisementRequest.getColumnPosition());

            startedDate = LocalDate.parse(createDtoAdvertisementRequest.getStartedDate());
            finishedDate = LocalDate.parse(createDtoAdvertisementRequest.getFinishedDate());
        } catch (Exception exception) {
            throw new Exception("적절하지 않은 요청입니다. (Please check the parameters)");
        }

        return AdvertisementRequest.builder()
                .id(null)
                .user(user)
                .advertisementType(advertisementType)
                .reviewStatus(AdvertisementReviewStatus.NON_APPROVAL)
                .title(createDtoAdvertisementRequest.getTitle())
                .detailDescription(createDtoAdvertisementRequest.getDetailDescription())
                .imagePath(createDtoAdvertisementRequest.getImagePath())
                .siteUrl(createDtoAdvertisementRequest.getSiteUrl())
                .rowPosition(rowPosition)
                .columnPosition(columnPosition)
                .reviewer(null)
                .startedDate(startedDate)
                .finishedDate(finishedDate)
                .build();
    }
}
