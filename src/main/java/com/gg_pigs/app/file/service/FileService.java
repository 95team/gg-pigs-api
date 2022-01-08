package com.gg_pigs.app.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    /**
     * @return Path of uploaded file
     * */
    String upload(MultipartFile file) throws IOException;
}
