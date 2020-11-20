package com.pangoapi.advertisement.entity;

import com.pangoapi.advertisement.dto.CreateDtoAdvertisement;
import com.pangoapi.advertisement.dto.UpdateDtoAdvertisement;
import com.pangoapi.advertisementType.entity.AdvertisementType;
import com.pangoapi.user.entity.User;
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
public class Advertisement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADVERTISEMENT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADVERTISEMENT_TYPE_ID")
    private AdvertisementType advertisementType;

    private String title;
    private String detailDescription;
    private String imagePath;
    private String siteUrl;
    private Long rowPosition;
    private Long columnPosition;
    private char isActivated;
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
    public void changeAdvertisement(UpdateDtoAdvertisement updateDtoAdvertisement) throws Exception {
        if(updateDtoAdvertisement.getTitle() != null) changeTitle(updateDtoAdvertisement.getTitle());
        if(updateDtoAdvertisement.getDetailDescription() != null) changeDetailDescription(updateDtoAdvertisement.getDetailDescription());
        if(updateDtoAdvertisement.getImagePath() != null) changeImagePath(updateDtoAdvertisement.getImagePath());
        if(updateDtoAdvertisement.getSiteUrl() != null) changeStieUrl(updateDtoAdvertisement.getSiteUrl());
        if(updateDtoAdvertisement.getRowPosition() != null) {
            try {
                changeRowPosition(Long.parseLong(updateDtoAdvertisement.getRowPosition()));
            } catch (Exception exception) {
                throw new Exception("적절하지 않은 요청입니다. (Please check the parameters)");
            }
        }
        if(updateDtoAdvertisement.getColumnPosition() != null) {
            try {
                changeColumnPosition(Long.parseLong(updateDtoAdvertisement.getColumnPosition()));
            } catch (Exception exception) {
                throw new Exception("적절하지 않은 요청입니다. (Please check the parameters)");
            }
        }
        if(updateDtoAdvertisement.getStartedDate() != null) {
            try {
                changeStartedDate(LocalDate.parse(updateDtoAdvertisement.getStartedDate()));
            } catch (NullPointerException | DateTimeParseException exception) {
                throw new IllegalArgumentException("적절하지 않은 날짜형식 입니다. (Please check the data 'startedDate')");
            }
        }
        if(updateDtoAdvertisement.getFinishedDate() != null) {
            try {
                changeFinishedDate(LocalDate.parse(updateDtoAdvertisement.getFinishedDate()));
            } catch (NullPointerException | DateTimeParseException exception) {
                throw new IllegalArgumentException("적절하지 않은 날짜형식 입니다. (Please check the data 'finishedDate')");
            }
        }
    }

    public static Advertisement createAdvertisement(CreateDtoAdvertisement createDtoAdvertisement, User user, AdvertisementType advertisementType) throws Exception {
        LocalDate startedDate = LocalDate.now(), finishedDate = LocalDate.now().plusMonths(1);
        Long rowPosition, columnPosition;
        try {
            rowPosition = Long.parseLong(createDtoAdvertisement.getRowPosition());
            columnPosition = Long.parseLong(createDtoAdvertisement.getColumnPosition());

            startedDate = LocalDate.parse(createDtoAdvertisement.getStartedDate());
            finishedDate = LocalDate.parse(createDtoAdvertisement.getFinishedDate());
        } catch (Exception exception) {
            throw new Exception("적절하지 않은 요청입니다. (Please check the parameters)");
        }

        return Advertisement.builder()
                .id(null)
                .user(user)
                .advertisementType(advertisementType)
                .title(createDtoAdvertisement.getTitle())
                .detailDescription(createDtoAdvertisement.getDetailDescription())
                .imagePath(createDtoAdvertisement.getImagePath())
                .siteUrl(createDtoAdvertisement.getSiteUrl())
                .rowPosition(rowPosition)
                .columnPosition(columnPosition)
                .isActivated('N')
                .startedDate(startedDate)
                .finishedDate(finishedDate)
                .build();
    }
}
