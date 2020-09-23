package com.pangoapi.common;

import java.util.regex.Pattern;

public class CommonDefinition {

    /** Image files */
    public static final String ALLOWABLE_MAXIMUM_IMAGE_FILE_SIZE_STRING = "20MiB";
    public static final Long ALLOWABLE_MAXIMUM_IMAGE_FILE_SIZE_LONG = (long) 20 * 1000 * 1000;

    public static final Pattern ALLOWABLE_IMAGE_FILE_EXTENSION_PATTERN = Pattern.compile("(?i)^(.+).(jpg|jpeg|gif|tif|tiff|bmp|png|raw|svg)$");
}
