package com.gg_pigs._common;

import java.util.regex.Pattern;

public class CommonDefinition {

    /** Cache */
    public static final int DEFAULT_CACHE_MAX_AGE = 30;
    public static final int ZERO_CACHE_MAX_AGE = 0;

    /** Poster layouts */
    public static final int POSTER_LAYOUT_SIZE = 6;

    /** Image files */
    public static final String ALLOWABLE_MAXIMUM_IMAGE_FILE_SIZE_STRING = "20MiB";
    public static final Long ALLOWABLE_MAXIMUM_IMAGE_FILE_SIZE_LONG = (long) 20 * 1000 * 1000;

    public static final Pattern ALLOWABLE_IMAGE_FILE_EXTENSION_PATTERN = Pattern.compile("(?i)^(.+).(jpg|jpeg|gif|tif|tiff|bmp|png|raw|svg)$");

    /** Poster-Slug pattern */
    public static final String ALLOWABLE_POSTER_SLUG_PATTERN_STRING = "[^가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9-]";
}
