package com.gg_pigs.app.poster.dto;

import com.gg_pigs.app.poster.entity.Poster;
import com.gg_pigs.app.poster.entity.PosterReviewStatus;
import com.gg_pigs.app.posterType.entity.PosterType;
import com.gg_pigs.app.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.gg_pigs.global.CommonDefinition.ALLOWABLE_POSTER_SLUG_PATTERN_STRING;
import static com.gg_pigs.global.CommonDefinition.POSTER_LAYOUT_SIZE;

public class PosterDto {

    public static class Create {
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        @Getter
        public static class RequestDto {
            private String title;
            private String userEmail;
            private String description;
            private String keywords;
            private String posterType;
            private String imagePath;
            private String siteUrl;
            private Long rowPosition;
            private Long columnPosition;
            private LocalDate startedDate;
            private LocalDate finishedDate;

            public Poster toEntity(User user, PosterType posterType) {
                return Poster.builder()
                        .id(null)
                        .user(user)
                        .posterType(posterType)
                        .title(title)
                        .description(description)
                        .keywords(keywords)
                        .imagePath(imagePath)
                        .siteUrl(siteUrl)
                        .rowPosition(rowPosition)
                        .columnPosition(columnPosition)
                        .reviewStatus(PosterReviewStatus.NEW)
                        .isActivated("N")
                        .startedDate(startedDate)
                        .finishedDate(finishedDate)
                        .build();
            }
        }

        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        @Getter
        public static class ResponseDto {
            private Long id;
            private String userEmail;
            private String title;
            private String description;
            private String keywords;
            private String slug;
            private String posterType;
            private String posterWidth;
            private String posterHeight;
            private String imagePath;
            private String siteUrl;
            private Long rowPosition;
            private Long columnPosition;
            private String reviewStatus;
            private String isActivated;
            private LocalDate startedDate;
            private LocalDate finishedDate;

            public static ResponseDto of(Poster poster) {
                String userEmail = (poster.getUser() != null) ? poster.getUser().getEmail() : "";
                String slug = generateSlugByTitle(poster.getTitle());

                return ResponseDto.builder()
                        .id(poster.getId())
                        .title(poster.getTitle())
                        .slug(slug)
                        .userEmail(userEmail)
                        .description(poster.getDescription())
                        .keywords(poster.getKeywords())
                        .posterType(poster.getPosterType().getType())
                        .imagePath(poster.getImagePath())
                        .siteUrl(poster.getSiteUrl())
                        .rowPosition(poster.getRowPosition())
                        .columnPosition(poster.getColumnPosition())
                        .reviewStatus(poster.getReviewStatus().name())
                        .isActivated(poster.getIsActivated())
                        .startedDate(poster.getStartedDate())
                        .finishedDate(poster.getFinishedDate())
                        .build();
            }

            private static String generateSlugByTitle(String title) {
                return !StringUtils.isEmpty(title)
                        ? title.replace(" ", "-").replaceAll(ALLOWABLE_POSTER_SLUG_PATTERN_STRING, "")
                        : "";
            }
        }
    }

    public static class Read {
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        @Getter
        public static class SearchConditionDto {
            String page;
            String userEmail;
            String isActivated;
            LocalDate currentDate;
            Long startIndexOfPage;
            Long lastIndexOfPage;

            public static SearchConditionDto of(String page, String userEmail, String isActivated, String isFilteredDate) {
                LocalDate currentDate = null;
                Long startIndexOfPage = null;
                Long lastIndexOfPage = null;
                if(page != null && !page.equals("-1")) {
                    startIndexOfPage = ((Long.parseLong(page) - 1) * POSTER_LAYOUT_SIZE) + 1;
                    lastIndexOfPage = (Long.parseLong(page) * POSTER_LAYOUT_SIZE);
                }
                if("Y".equalsIgnoreCase(isFilteredDate)) {
                    currentDate = LocalDate.now();
                }

                return SearchConditionDto.builder()
                        .startIndexOfPage(startIndexOfPage)
                        .lastIndexOfPage(lastIndexOfPage)
                        .userEmail(userEmail)
                        .isActivated(isActivated)
                        .currentDate(currentDate)
                        .build();
            }
        }

        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        @Getter
        public static class ResponseDto {
            private Long id;
            private String userEmail;
            private String title;
            private String description;
            private String keywords;
            private String slug;
            private String posterType;
            private String posterWidth;
            private String posterHeight;
            private String imagePath;
            private String siteUrl;
            private Long rowPosition;
            private Long columnPosition;
            private String reviewStatus;
            private String isActivated;
            private String startedDate;
            private String finishedDate;

            public static ResponseDto of(Poster poster) {
                String userEmail = (poster.getUser() != null) ? poster.getUser().getEmail() : "";
                String slug = generateSlugByTitle(poster.getTitle());

                return ResponseDto.builder()
                        .id(poster.getId())
                        .userEmail(userEmail)
                        .title(poster.getTitle())
                        .slug(slug)
                        .description(poster.getDescription())
                        .keywords(poster.getKeywords())
                        .posterType(poster.getPosterType().getType())
                        .posterWidth(poster.getPosterType().getWidth())
                        .posterHeight(poster.getPosterType().getHeight())
                        .imagePath(poster.getImagePath())
                        .siteUrl(poster.getSiteUrl())
                        .rowPosition(poster.getRowPosition())
                        .columnPosition(poster.getColumnPosition())
                        .reviewStatus(poster.getReviewStatus().name())
                        .isActivated(poster.getIsActivated())
                        .startedDate(poster.getStartedDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
                        .finishedDate(poster.getFinishedDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
                        .build();
            }

            private static String generateSlugByTitle(String title) {
                return !StringUtils.isEmpty(title)
                        ? title.replace(" ", "-").replaceAll(ALLOWABLE_POSTER_SLUG_PATTERN_STRING, "")
                        : "";
            }
        }
    }

    public static class Update {
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        @Getter
        public static class RequestDto {
            private String userEmail;
            private String title;
            private String description;
            private String keywords;
            private String posterType;
            private String imagePath;
            private String siteUrl;
            private Long rowPosition;
            private Long columnPosition;
            private LocalDate startedDate;
            private LocalDate finishedDate;

            public Poster toEntity(User user, PosterType posterType) {
                return Poster.builder()
                        .user(user)
                        .posterType(posterType)
                        .title(title)
                        .description(description)
                        .keywords(keywords)
                        .imagePath(imagePath)
                        .siteUrl(siteUrl)
                        .rowPosition(rowPosition)
                        .columnPosition(columnPosition)
                        .startedDate(startedDate)
                        .finishedDate(finishedDate)
                        .build();
            }
        }

        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        @Getter
        public static class ResponseDto {
            private Long id;
            private String userEmail;
            private String title;
            private String description;
            private String keywords;
            private String slug;
            private String posterType;
            private String posterWidth;
            private String posterHeight;
            private String imagePath;
            private String siteUrl;
            private Long rowPosition;
            private Long columnPosition;
            private String reviewStatus;
            private String isActivated;
            private LocalDate startedDate;
            private LocalDate finishedDate;

            public static Update.ResponseDto of(Poster poster) {
                String userEmail = (poster.getUser() != null) ? poster.getUser().getEmail() : "";
                String slug = generateSlugByTitle(poster.getTitle());

                return Update.ResponseDto.builder()
                        .id(poster.getId())
                        .title(poster.getTitle())
                        .slug(slug)
                        .userEmail(userEmail)
                        .description(poster.getDescription())
                        .keywords(poster.getKeywords())
                        .posterType(poster.getPosterType().getType())
                        .imagePath(poster.getImagePath())
                        .siteUrl(poster.getSiteUrl())
                        .rowPosition(poster.getRowPosition())
                        .columnPosition(poster.getColumnPosition())
                        .reviewStatus(poster.getReviewStatus().name())
                        .isActivated(poster.getIsActivated())
                        .startedDate(poster.getStartedDate())
                        .finishedDate(poster.getFinishedDate())
                        .build();
            }

            private static String generateSlugByTitle(String title) {
                return !StringUtils.isEmpty(title)
                        ? title.replace(" ", "-").replaceAll(ALLOWABLE_POSTER_SLUG_PATTERN_STRING, "")
                        : "";
            }
        }
    }
}
