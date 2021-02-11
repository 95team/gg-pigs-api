package com.gg_pigs._common;

import java.util.regex.Pattern;

public class CommonDefinition {

    /** Advertisement layouts */
    public static final int ADVERTISEMENT_LAYOUT_SIZE = 6;
    public static final int POSTER_LAYOUT_SIZE = 6;

    /** Image files */
    public static final String ALLOWABLE_MAXIMUM_IMAGE_FILE_SIZE_STRING = "20MiB";
    public static final Long ALLOWABLE_MAXIMUM_IMAGE_FILE_SIZE_LONG = (long) 20 * 1000 * 1000;

    public static final Pattern ALLOWABLE_IMAGE_FILE_EXTENSION_PATTERN = Pattern.compile("(?i)^(.+).(jpg|jpeg|gif|tif|tiff|bmp|png|raw|svg)$");

    /** Mail */
    public static final Pattern ALLOWABLE_EMAIL_FORMAT_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
}
