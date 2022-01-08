package com.gg_pigs.global;

import java.util.Arrays;
import java.util.List;

public class CommonDefinition {

    /** Cache */
    public static final int DEFAULT_CACHE_MAX_AGE = 30;
    public static final int ZERO_CACHE_MAX_AGE = 0;

    /** Poster layouts */
    public static final int POSTER_LAYOUT_SIZE = 6;

    /** Image files */
    public static final String ALLOWABLE_MAXIMUM_IMAGE_FILE_SIZE_STRING = "20MiB";
    public static final Long ALLOWABLE_MAXIMUM_IMAGE_FILE_SIZE_LONG = (long) 20 * 1000 * 1000;
    public static final Long ALLOWABLE_MINIMUM_IMAGE_FILE_SIZE_LONG = (long) 0;

    public static final List<String> ALLOWABLE_IMAGE_FILE_EXTENSION_LIST = Arrays.asList("JPG", "JPEG", "GIF", "TIF", "TIFF", "BMP", "PNG", "RAW", "SVG");

    /** Poster-Slug pattern */
    public static final String ALLOWABLE_POSTER_SLUG_PATTERN_STRING = "[^가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9-]";
}
