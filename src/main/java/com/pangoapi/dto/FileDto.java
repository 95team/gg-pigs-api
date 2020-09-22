package com.pangoapi.dto;

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

    public boolean checkFileExtension() {
        boolean result = false;

        if(fileType.equalsIgnoreCase("image")) {
            result = true;
            Pattern pattern = Pattern.compile("(?i)^(.+).(jpg|jpeg|gif|tif|bmp|png)$");
            Matcher matcher = pattern.matcher(file.getOriginalFilename());

            if(matcher.find()) {
                String fileName = matcher.group(1);
                if (!fileName.equals(" ")) result = true;
            }
        }

        return result;
    }

    public String getFileName() {
        String fileName = null;

        if(fileType.equalsIgnoreCase("image")) {
            Pattern pattern = Pattern.compile("(?i)^(.+).(jpg|jpeg|gif|tif|bmp|png)$");
            Matcher matcher = pattern.matcher(file.getOriginalFilename());

            if(matcher.find()) fileName = matcher.group(1);
        }

        return fileName;
    }

    public String getFileExtension() {
        String fileExtension = null;

        if(fileType.equalsIgnoreCase("image")) {
            Pattern pattern = Pattern.compile("(?i)^(.+).(jpg|jpeg|gif|tif|bmp|png)$");
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
                    + (System.currentTimeMillis() % 1000000)
                    + "_"
                    + (int)(Math.random() * randomNumberRange)
                    + "."
                    + getFileExtension().toLowerCase();
        }

        return randomFileName;
    }

    public String uploadFile(String uploadDirectory) {
        String uploadFileName = makeRandomFileName();

        if(serviceType.equalsIgnoreCase("advertisement")) uploadDirectory += "advertisements/";

        File uploadFile = new File(uploadDirectory + uploadFileName);

        if(uploadFile.exists()) return null;

        try {
            file.transferTo(uploadFile);
        } catch (Exception e) {
            return null;
        }

        return uploadFileName;
    }
}
