package com.gg_pigs.file.service;

import com.gg_pigs._common.CommonDefinition;
import com.gg_pigs._common.utility.github.GitHubClient;
import com.gg_pigs.file.dto.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FileServiceGitHubImpl implements FileService {

    @Autowired private Environment environment;
    @Autowired private GitHubClient gitHubClient;

    @Override
    @Transactional
    public String uploadImage(FileDto fileDto) throws IOException {
        return this.uploadImageToGitHub(fileDto);
    }

    private String uploadImageToGitHub(FileDto fileDto) throws IOException {
        if(!fileDto.checkFileSize()) throw new IllegalArgumentException("데이터를 업로드할 수 없습니다. (Check the file size. File size is allowed up to " + CommonDefinition.ALLOWABLE_MAXIMUM_IMAGE_FILE_SIZE_STRING + ".)");
        if(!fileDto.checkFileExtension()) throw new IllegalArgumentException("데이터를 업로드할 수 없습니다. (Check the file extension or fileType.)");

        String uploadImagePath;
        String uploadRawPath = environment.getProperty("application.github.image.raw-path");
        String uploadContentPath = null;
        int tryCount = 0;
        int tryThreshold = 5;

        for(; tryCount < tryThreshold; tryCount++){
            uploadContentPath = gitHubClient.uploadContent(fileDto);

            if(!StringUtils.isEmpty(uploadContentPath)) break;
        }
        if(tryCount >= tryThreshold) throw new IOException("데이터를 업로드할 수 없습니다. (Check the server status)");

        uploadImagePath = uploadRawPath + uploadContentPath;

        return uploadImagePath;
    }

}
