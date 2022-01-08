package com.gg_pigs.app.poster.entity;

import com.gg_pigs.app.poster.dto.CreateDtoPoster;
import com.gg_pigs.app.poster.dto.UpdateDtoPoster;
import com.gg_pigs.app.posterType.entity.PosterType;
import com.gg_pigs.app.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Poster {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POSTER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POSTER_TYPE_ID")
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

    private Long rowPosition;
    private Long columnPosition;
    private char isActivated;
    private LocalDate startedDate;
    private LocalDate finishedDate;

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

    public void changeIsActivatedToActivated() { this.isActivated = 'Y'; }

    public void changeIsActivatedToDeactivated() { this.isActivated = 'N'; }

    public void changeStartedDate(LocalDate startedDate) { this.startedDate = startedDate; }

    public void changeFinishedDate(LocalDate finishedDate) { this.finishedDate = finishedDate; }

    /**
     * @description
     * 사용자가 업데이트 요청할 수 있는 필드/메소드 입니다.
     * 해당 메소드에 포함되지 않은 ReviewStatus, Reviewer 등의 필드는 관리자가 업데이트 요청할 수 있는 필드입니다. 즉, 관리자 관련 필드입니다.
     * */
    public void changePoster(UpdateDtoPoster updateDtoPoster) throws Exception {
        if(updateDtoPoster.getTitle() != null) changeTitle(updateDtoPoster.getTitle());
        if(updateDtoPoster.getDescription() != null) changeDescription(updateDtoPoster.getDescription());
        if(updateDtoPoster.getKeywords() != null) changeKeywords(updateDtoPoster.getKeywords());
        if(updateDtoPoster.getImagePath() != null) changeImagePath(updateDtoPoster.getImagePath());
        if(updateDtoPoster.getSiteUrl() != null) changeSiteUrl(updateDtoPoster.getSiteUrl());
        if(updateDtoPoster.getRowPosition() != null) {
            try {
                changeRowPosition(Long.parseLong(updateDtoPoster.getRowPosition()));
            } catch (Exception exception) {
                throw new Exception("적절하지 않은 요청입니다. (Please check the parameters)");
            }
        }
        if(updateDtoPoster.getColumnPosition() != null) {
            try {
                changeColumnPosition(Long.parseLong(updateDtoPoster.getColumnPosition()));
            } catch (Exception exception) {
                throw new Exception("적절하지 않은 요청입니다. (Please check the parameters)");
            }
        }
        if(updateDtoPoster.getStartedDate() != null) {
            try {
                changeStartedDate(LocalDate.parse(updateDtoPoster.getStartedDate()));
            } catch (NullPointerException | DateTimeParseException exception) {
                throw new IllegalArgumentException("적절하지 않은 날짜형식 입니다. (Please check the data 'startedDate')");
            }
        }
        if(updateDtoPoster.getFinishedDate() != null) {
            try {
                changeFinishedDate(LocalDate.parse(updateDtoPoster.getFinishedDate()));
            } catch (NullPointerException | DateTimeParseException exception) {
                throw new IllegalArgumentException("적절하지 않은 날짜형식 입니다. (Please check the data 'finishedDate')");
            }
        }
    }

    public static Poster createPoster(CreateDtoPoster createDtoPoster, User user, PosterType posterType) throws Exception {
        LocalDate startedDate, finishedDate;
        Long rowPosition, columnPosition;
        try {
            rowPosition = Long.parseLong(createDtoPoster.getRowPosition());
            columnPosition = Long.parseLong(createDtoPoster.getColumnPosition());

            startedDate = LocalDate.parse(createDtoPoster.getStartedDate());
            finishedDate = LocalDate.parse(createDtoPoster.getFinishedDate());
        } catch (Exception exception) {
            throw new Exception("적절하지 않은 요청입니다. (Please check the parameters)");
        }

        return Poster.builder()
                .id(null)
                .user(user)
                .posterType(posterType)
                .title(createDtoPoster.getTitle())
                .description(createDtoPoster.getDescription())
                .keywords(createDtoPoster.getKeywords())
                .imagePath(createDtoPoster.getImagePath())
                .siteUrl(createDtoPoster.getSiteUrl())
                .rowPosition(rowPosition)
                .columnPosition(columnPosition)
                .isActivated('N')
                .startedDate(startedDate)
                .finishedDate(finishedDate)
                .build();
    }
}
