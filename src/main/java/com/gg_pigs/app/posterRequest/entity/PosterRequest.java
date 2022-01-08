package com.gg_pigs.app.posterRequest.entity;

import com.gg_pigs.app.posterType.entity.PosterType;
import com.gg_pigs.app.posterRequest.dto.CreateDtoPosterRequest;
import com.gg_pigs.app.posterRequest.dto.UpdateDtoPosterRequest;
import com.gg_pigs.app.user.entity.User;
import com.gg_pigs.app.poster.entity.PosterReviewStatus;
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
public class PosterRequest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POSTER_REQUEST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POSTER_TYPE_ID")
    private PosterType posterType;

    @Enumerated(EnumType.STRING)
    @Column(name = "REVIEW_STATUS", length = 32)
    private PosterReviewStatus reviewStatus;

    @Column(length = 128)
    private String title;

    @Column(length = 255)
    private String description;

    @Column(length = 255)
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

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void changeImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void changeSiteUrl(String siteUrl) { this.siteUrl = siteUrl; }

    public void changeRowPosition(Long rowPosition) { this.rowPosition = rowPosition; }

    public void changeColumnPosition(Long columnPosition) { this.columnPosition = columnPosition; }

    public void changeReviewStatusToApproval() { this.reviewStatus = PosterReviewStatus.APPROVAL; }

    public void changeReviewStatusToPending() { this.reviewStatus = PosterReviewStatus.PENDING; }

    public void changeReviewStatusToNonApproval() { this.reviewStatus = PosterReviewStatus.NON_APPROVAL; }

    public void changeReviewer(String reviewer) { this.reviewer = reviewer; }

    public void changeStartedDate(LocalDate startedDate) { this.startedDate = startedDate; }

    public void changeFinishedDate(LocalDate finishedDate) { this.finishedDate = finishedDate; }

    /**
     * @description
     * 사용자가 업데이트 요청할 수 있는 필드/메소드 입니다.
     * 해당 메소드에 포함되지 않은 ReviewStatus, Reviewer 등의 필드는 관리자가 업데이트 요청할 수 있는 필드입니다. 즉, 관리자 관련 필드입니다.
     * */
    public void changePosterRequest(UpdateDtoPosterRequest updateDtoPosterRequest) throws Exception {
        if(updateDtoPosterRequest.getTitle() != null) changeTitle(updateDtoPosterRequest.getTitle());
        if(updateDtoPosterRequest.getDescription() != null) changeDescription(updateDtoPosterRequest.getDescription());
        if(updateDtoPosterRequest.getKeywords() != null) changeKeywords(updateDtoPosterRequest.getKeywords());
        if(updateDtoPosterRequest.getImagePath() != null) changeImagePath(updateDtoPosterRequest.getImagePath());
        if(updateDtoPosterRequest.getSiteUrl() != null) changeSiteUrl(updateDtoPosterRequest.getSiteUrl());
        if(updateDtoPosterRequest.getRowPosition() != null) {
            try {
                changeRowPosition(Long.parseLong(updateDtoPosterRequest.getRowPosition()));
            } catch (Exception exception) {
                throw new Exception("적절하지 않은 요청입니다. (Please check the parameters)");
            }
        }
        if(updateDtoPosterRequest.getColumnPosition() != null) {
            try {
                changeColumnPosition(Long.parseLong(updateDtoPosterRequest.getColumnPosition()));
            } catch (Exception exception) {
                throw new Exception("적절하지 않은 요청입니다. (Please check the parameters)");
            }
        }
        if(updateDtoPosterRequest.getStartedDate() != null) {
            try {
                changeStartedDate(LocalDate.parse(updateDtoPosterRequest.getStartedDate()));
            } catch (NullPointerException | DateTimeParseException exception) {
                throw new IllegalArgumentException("적절하지 않은 날짜형식 입니다. (Please check the data 'startedDate')");
            }
        }
        if(updateDtoPosterRequest.getFinishedDate() != null) {
            try {
                changeFinishedDate(LocalDate.parse(updateDtoPosterRequest.getFinishedDate()));
            } catch (NullPointerException | DateTimeParseException exception) {
                throw new IllegalArgumentException("적절하지 않은 날짜형식 입니다. (Please check the data 'finishedDate')");
            }
        }
    }

    public static PosterRequest createPosterRequest(CreateDtoPosterRequest createDtoPosterRequest, User user, PosterType posterType) throws Exception {
        Long rowPosition, columnPosition;
        LocalDate startedDate; LocalDate finishedDate;

        try {
            rowPosition = Long.parseLong(createDtoPosterRequest.getRowPosition());
            columnPosition = Long.parseLong(createDtoPosterRequest.getColumnPosition());

            startedDate = LocalDate.parse(createDtoPosterRequest.getStartedDate());
            finishedDate = LocalDate.parse(createDtoPosterRequest.getFinishedDate());
        } catch (Exception exception) {
            throw new Exception("적절하지 않은 요청입니다. (Please check the parameters)");
        }

        return PosterRequest.builder()
                .id(null)
                .user(user)
                .posterType(posterType)
                .reviewStatus(PosterReviewStatus.NEW)
                .title(createDtoPosterRequest.getTitle())
                .description(createDtoPosterRequest.getDescription())
                .keywords(createDtoPosterRequest.getKeywords())
                .imagePath(createDtoPosterRequest.getImagePath())
                .siteUrl(createDtoPosterRequest.getSiteUrl())
                .rowPosition(rowPosition)
                .columnPosition(columnPosition)
                .reviewer(null)
                .startedDate(startedDate)
                .finishedDate(finishedDate)
                .build();
    }
}
