package com.gg_pigs.app.file.service;

import com.gg_pigs.global.exception.BadRequestException;
import com.gg_pigs.modules.github.GitHubClient;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.gg_pigs.global.CommonDefinition.ALLOWABLE_MAXIMUM_IMAGE_FILE_SIZE_LONG;
import static com.gg_pigs.global.CommonDefinition.ALLOWABLE_MAXIMUM_IMAGE_FILE_SIZE_STRING;
import static com.gg_pigs.global.utility.FileUtil.getFileSize;
import static com.gg_pigs.global.utility.FileUtil.isImageFile;
import static com.gg_pigs.global.utility.FileUtil.makeRandomFileName;

@RequiredArgsConstructor
@Service
public class GitHubFileService implements FileService {

    private final int UPLOAD_TRY_THRESHOLD = 5;

    private final Environment environment;
    private final GitHubClient gitHubClient;

    @Override
    public String upload(MultipartFile file) throws IOException {
        if(isImageFile(file)) {
            return this.uploadImage2Server(file);
        }
        throw new BadRequestException("적절하지 않은 요청입니다. (Check the file type)");
    }

    private String uploadImage2Server(MultipartFile file) throws IOException {
        if(getFileSize(file) > ALLOWABLE_MAXIMUM_IMAGE_FILE_SIZE_LONG) {
            throw new BadRequestException("데이터를 업로드할 수 없습니다. (Check the file size. File size is allowed up to " + ALLOWABLE_MAXIMUM_IMAGE_FILE_SIZE_STRING + ".)");
        }

        String uploadImagePath;
        String uploadRawPath = environment.getProperty("application.github.image.raw-path");
        String uploadContentPath = null;
        int tryCount = 0;

        for(; tryCount < UPLOAD_TRY_THRESHOLD; tryCount++){
            uploadContentPath = gitHubClient.uploadImageContent(file, makeRandomFileName(file.getOriginalFilename()));
            if(!StringUtils.isEmpty(uploadContentPath)) {
                break;
            }
        }
        if(tryCount >= UPLOAD_TRY_THRESHOLD) {
            throw new IOException("데이터를 업로드할 수 없습니다. (Check the upload server's status)");
        }

        uploadImagePath = uploadRawPath + uploadContentPath;
        return uploadImagePath;
    }
}
