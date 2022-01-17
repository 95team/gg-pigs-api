package com.gg_pigs.app.poster.entity;

import com.gg_pigs.app.posterType.entity.PosterType;
import com.gg_pigs.app.user.entity.User;
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
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
public class Poster {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poster_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poster_type_id")
    private PosterType posterType;

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

    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    private PosterReviewStatus reviewStatus;

    private Long rowPosition;
    private Long columnPosition;
    private String isActivated;
    private LocalDate startedDate;
    private LocalDate finishedDate;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeKeywords(String keywords) { this.keywords = keywords; }

    public void changeImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void changeSiteUrl(String siteUrl) { this.siteUrl = siteUrl; }

    public void changeRowPosition(Long rowPosition) { this.rowPosition = rowPosition; }

    public void changeColumnPosition(Long columnPosition) { this.columnPosition = columnPosition; }

    public void changeReviewStatusToApproval() { this.reviewStatus = PosterReviewStatus.APPROVAL; }

    public void changeReviewStatusToPending() { this.reviewStatus = PosterReviewStatus.PENDING; }

    public void changeReviewStatusToNonApproval() { this.reviewStatus = PosterReviewStatus.NON_APPROVAL; }

    public void changeIsActivatedToActivated() { this.isActivated = "Y"; }

    public void changeIsActivatedToDeactivated() { this.isActivated = "N"; }

    public void changeStartedDate(LocalDate startedDate) { this.startedDate = startedDate; }

    public void changeFinishedDate(LocalDate finishedDate) { this.finishedDate = finishedDate; }

    public void changePoster(Poster newPoster) {
        changeTitle(newPoster.getTitle());
        changeDescription(newPoster.getDescription());
        changeKeywords(newPoster.getKeywords());
        changeImagePath(newPoster.getImagePath());
        changeSiteUrl(newPoster.getSiteUrl());
        changeRowPosition(newPoster.getRowPosition());
        changeColumnPosition(newPoster.getColumnPosition());
        changeStartedDate(newPoster.getStartedDate());
        changeFinishedDate(newPoster.getFinishedDate());
    }

    public boolean isNew() {
        return this.reviewStatus == PosterReviewStatus.NEW;
    }

    public boolean isApproval() {
        return this.reviewStatus == PosterReviewStatus.APPROVAL;
    }

    public boolean isPending() {
        return this.reviewStatus == PosterReviewStatus.PENDING;
    }

    public boolean isNonApproval() {
        return this.reviewStatus == PosterReviewStatus.NON_APPROVAL;
    }
}
