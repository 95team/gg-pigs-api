package com.gg_pigs.file.dto;

import com.gg_pigs._common.CommonDefinition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class FileDto {

    private MultipartFile file;
    private String fileType;
    private String serviceType;

    public boolean checkFileSize() {
        boolean result = false;

        if(fileType.equalsIgnoreCase("image")) {
            if(getFileSize() >= getAllowableMinimumFileSize() && getFileSize() <= getAllowableMaximumFileSize()) {
                result = true;
            }
        }

        return result;
    }

    public boolean checkFileExtension() {
        boolean result = false;

        if(fileType.equalsIgnoreCase("image")) {
            result = true;
            Pattern pattern = CommonDefinition.ALLOWABLE_IMAGE_FILE_EXTENSION_PATTERN;
            Matcher matcher = pattern.matcher(file.getOriginalFilename());

            if(matcher.find()) {
                String fileName = matcher.group(1);
                if (!fileName.equals(" ")) result = true;
            }
        }

        return result;
    }

    public long getFileSize() {
        return file.getSize();
    }

    public long getAllowableMaximumFileSize() {
        // 파일 사이즈 단위는 KiB, MiB, GiB, TiB 단위로 처리합니다.
        long allowableMaximumFileSize = -1;

        if(fileType.equalsIgnoreCase("image")) {
            allowableMaximumFileSize = CommonDefinition.ALLOWABLE_MAXIMUM_IMAGE_FILE_SIZE_LONG;
        }

        return allowableMaximumFileSize;
    }

    public long getAllowableMinimumFileSize() {
        // 파일 사이즈 단위는 KiB, MiB, GiB, TiB 단위로 처리합니다.
        long allowableMinimumFileSize = -1;

        if(fileType.equalsIgnoreCase("image")) {
            allowableMinimumFileSize = 0;
        }

        return allowableMinimumFileSize;
    }

    public String getFileName() {
        String fileName = null;

        if(fileType.equalsIgnoreCase("image")) {
            Pattern pattern = CommonDefinition.ALLOWABLE_IMAGE_FILE_EXTENSION_PATTERN;
            Matcher matcher = pattern.matcher(file.getOriginalFilename());

            if(matcher.find()) fileName = matcher.group(1);
        }

        return fileName;
    }

    public String getFileExtension() {
        String fileExtension = null;

        if(fileType.equalsIgnoreCase("image")) {
            Pattern pattern = CommonDefinition.ALLOWABLE_IMAGE_FILE_EXTENSION_PATTERN;
            Matcher matcher = pattern.matcher(file.getOriginalFilename());

            if(matcher.find()) fileExtension = matcher.group(2);
        }

        return fileExtension;
    }

    public String makeRandomFileName() {
        String randomFileName = null;
        int randomNumberRange = 10;

        if(fileType.equalsIgnoreCase("image")) {
            randomFileName = getFileName().trim().replace(' ', '_')
                    + "_"
                    + (System.currentTimeMillis() % 1000000)    // 파일명이 길어지는 것을 방지하기 위해, milliseconds 값의 뒤의 6자리만 사용합니다.
                    + "_"
                    + (int)(Math.random() * randomNumberRange)
                    + "."
                    + getFileExtension().toLowerCase();
        }

        return randomFileName;
    }

    public String uploadFile(String uploadDirectory) {
        String uploadFileName = makeRandomFileName();

        File uploadFile = new File(uploadDirectory + uploadFileName);

        if(uploadFile.exists()) {
            return null;
        }

        try {
            file.transferTo(uploadFile);
        } catch (Exception e) {
            return null;
        }

        return uploadFileName;
    }
}
