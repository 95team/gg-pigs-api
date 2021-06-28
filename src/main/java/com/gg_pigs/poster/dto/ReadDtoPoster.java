package com.gg_pigs.poster.dto;

import com.gg_pigs.poster.entity.Poster;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.time.format.DateTimeFormatter;

import static com.gg_pigs._common.CommonDefinition.ALLOWABLE_POSTER_SLUG_PATTERN_STRING;


@AllArgsConstructor
@Builder
@Getter
public class ReadDtoPoster {

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
    private String rowPosition;
    private String columnPosition;
    private Character isActivated;
    private String startedDate;
    private String finishedDate;

    public static ReadDtoPoster of(Poster poster) {
        String userEmail = (poster.getUser() != null) ? poster.getUser().getEmail() : "";
        String slug = generateSlugByTitle(poster.getTitle());

        return ReadDtoPoster.builder()
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
                .rowPosition(Long.toString(poster.getRowPosition()))
                .columnPosition(Long.toString(poster.getColumnPosition()))
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
