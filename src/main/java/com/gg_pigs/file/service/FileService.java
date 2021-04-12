package com.gg_pigs.file.service;

import com.gg_pigs.file.dto.FileDto;

import java.io.IOException;

public interface FileService {

    /**
     * @return Path of uploaded file
     * */
    String uploadImage(FileDto fileDto) throws IOException;
}
