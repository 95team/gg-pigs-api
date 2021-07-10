package com.gg_pigs.file.service;

import com.gg_pigs._common.enums.FileType;
import com.gg_pigs._common.exception.BadRequestException;
import com.gg_pigs._common.utility.github.GitHubClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.gg_pigs._common.CommonDefinition.ALLOWABLE_MAXIMUM_IMAGE_FILE_SIZE_STRING;
import static com.gg_pigs._common.CommonDefinition.ALLOWABLE_MINIMUM_IMAGE_FILE_SIZE_LONG;
import static com.gg_pigs._common.utility.FileUtil.getFileExtension;
import static com.gg_pigs._common.utility.FileUtil.getFileSize;
import static com.gg_pigs._common.utility.FileUtil.isImageFile;
import static com.gg_pigs._common.utility.FileUtil.makeRandomFileName;
import static org.apache.commons.lang3.EnumUtils.isValidEnumIgnoreCase;

@RequiredArgsConstructor
@Service
public class GitHubFileService implements FileService {

    @Autowired private Environment environment;
    @Autowired private GitHubClient gitHubClient;

    @Override
    public String upload(MultipartFile file) throws IOException {
        if(!isValidEnumIgnoreCase(FileType.class, getFileExtension(file))) {
            throw new BadRequestException("적절하지 않은 요청입니다. (Check the file extension or fileType.)");
        }

        if(isImageFile(file)) {
            return this.uploadImage2Server(file);
        }
        return null;
    }

    private String uploadImage2Server(MultipartFile file) throws IOException {
        if(getFileSize(file) > ALLOWABLE_MINIMUM_IMAGE_FILE_SIZE_LONG) {
            throw new BadRequestException("데이터를 업로드할 수 없습니다. (Check the file size. File size is allowed up to " + ALLOWABLE_MAXIMUM_IMAGE_FILE_SIZE_STRING + ".)");
        }

        String uploadImagePath;
        String uploadRawPath = environment.getProperty("application.github.image.raw-path");
        String uploadContentPath = null;
        int tryCount = 0;
        int tryThreshold = 5;

        for(; tryCount < tryThreshold; tryCount++){
            uploadContentPath = gitHubClient.uploadImageContent(file, makeRandomFileName(file.getOriginalFilename()));
            if(!StringUtils.isEmpty(uploadContentPath)) {
                break;
            }
        }
        if(tryCount >= tryThreshold) {
            throw new IOException("데이터를 업로드할 수 없습니다. (Check the server status)");
        }

        uploadImagePath = uploadRawPath + uploadContentPath;
        return uploadImagePath;
    }
}
