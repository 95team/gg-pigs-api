package com.pangoapi.file.controller;

import com.pangoapi._common.dto.ApiResponse;
import com.pangoapi.file.dto.FileDto;
import com.pangoapi.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class FileApiController {

    private final FileService fileService;

    @PostMapping("/api/v1/files")
    public ApiResponse uploadOneImage(HttpServletRequest httpServletRequest) throws IOException {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) httpServletRequest;
        MultipartFile file = multipartHttpServletRequest.getFile("file");
        String fileType = httpServletRequest.getParameter("fileType");
        String serviceType = httpServletRequest.getParameter("serviceType");

        String uploadImageUrl = fileService.uploadImageToGitHub(
                FileDto.builder()
                        .file(file)
                        .fileType(fileType)
                        .serviceType(serviceType)
                        .build());

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), uploadImageUrl);
    }
}
