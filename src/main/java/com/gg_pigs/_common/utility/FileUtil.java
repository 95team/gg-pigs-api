package com.gg_pigs._common.utility;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;

import static com.gg_pigs._common.CommonDefinition.ALLOWABLE_IMAGE_FILE_EXTENSION_LIST;

public class FileUtil {

    private static String getExtension(@Nullable String fileName) {
        return StringUtils.isEmpty(fileName) ? "" : fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    public static String getFileExtension(String fileName) {
        return getExtension(fileName);
    }

    public static String getFileExtension(MultipartFile file) {
        return getExtension(file.getOriginalFilename());
    }

    public static long getFileSize(MultipartFile file) {
        return file.getSize();
    }

    public static boolean isImageFile(MultipartFile file) {
        return ALLOWABLE_IMAGE_FILE_EXTENSION_LIST.contains(getFileExtension(file).toUpperCase());
    }

    public static boolean isImageFile(String fileName) {
        return ALLOWABLE_IMAGE_FILE_EXTENSION_LIST.contains(getFileExtension(fileName).toUpperCase());
    }

    public static String makeRandomFileName(@Nullable String fileName) {
        if(StringUtils.isEmpty(fileName)) {
            return "";
        }

        String randomFileName;
        int randomNumberRange = 100;

        randomFileName = fileName.trim().replace(' ', '_')
                + "_"
                + (System.currentTimeMillis() % 1000000)    // 파일명이 길어지는 것을 방지하기 위해, milliseconds 값의 뒤의 6자리만 사용합니다.
                + "_"
                + (int) (Math.random() * randomNumberRange)
                + "."
                + getFileExtension(fileName).toLowerCase();

        return randomFileName;
    }
}
