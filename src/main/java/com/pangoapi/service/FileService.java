package com.pangoapi.service;

import com.pangoapi.common.CommonDefinition;
import com.pangoapi.dto.FileDto;
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
public class FileService {

    @Autowired private Environment environment;

    public String uploadOneImage(FileDto fileDto) throws IOException {
        if(!fileDto.checkFileSize()) throw new IllegalArgumentException("데이터를 업로드할 수 없습니다. (Check the file size. File size is allowed up to " + CommonDefinition.ALLOWABLE_MAXIMUM_IMAGE_FILE_SIZE_STRING + ".)");
        if(!fileDto.checkFileExtension()) throw new IllegalArgumentException("데이터를 업로드할 수 없습니다. (Check the file extension or fileType.)");

        final String uploadServerHost = environment.getProperty("application.server.host");
        final String uploadDirectory = environment.getProperty("application.upload.image-directory");
        int tryCount = 0;
        int tryThreshold = 10;
        String uploadImageUrl = uploadServerHost + "images/";
        String uploadImageFileName = null;

        for(; tryCount < tryThreshold; tryCount++){
            uploadImageFileName = fileDto.uploadFile(uploadDirectory);
            if(!StringUtils.isEmpty(uploadImageFileName)) break;
        }
        if(tryCount >= tryThreshold) throw new IOException("데이터를 업로드할 수 없습니다. (Check the server status)");

        if(fileDto.getServiceType().equalsIgnoreCase("advertisement")) {
            uploadImageUrl += "advertisements/";
        }
        uploadImageUrl += uploadImageFileName;

        return uploadImageUrl;
    }

}
