package com.pangoapi._common.utility.github;

import com.google.gson.Gson;
import com.pangoapi._common.enums.HistoryLogAction;
import com.pangoapi._common.utility.github.dto.response.GitHubErrorResponse;
import com.pangoapi._common.utility.github.dto.response.GitHubResponse;
import com.pangoapi.file.dto.FileDto;
import com.pangoapi.historyLog.service.HistoryLogService;
import com.pangoapi.user.entity.User;
import com.pangoapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class GitHubClient {

    private String BASE_URL = "https://api.github.com";

    @Value("${application.github.token}")
    private String token;

    @Value("${application.github.user}")
    private String user;

    @Value("${application.github.image.repo}")
    private String imageRepo;

    @Value("${application.github.image.repo-path}")
    private String imagePath;

    @Value("${application.admin.email}")
    private String adminUserEmail;

    private Retrofit retrofit;
    private GitHub github;
    private Gson gson;
    private User adminUser;

    private final UserRepository userRepository;
    private final HistoryLogService historyLogService;

    @PostConstruct
    private void init() {
        /**
         * Initialize objects
         * 1. API Client library (Retrofit)
         * 2. Gson object
         * */

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        github = retrofit.create(GitHub.class);

        gson = new Gson();
    }


    public String uploadContent(FileDto fileDto) throws IOException {
        adminUser = userRepository.findUserByEmail(adminUserEmail).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));

        String uploadContentPath = null;

        if(fileDto.getFileType().equalsIgnoreCase("image")) {
            uploadContentPath = this.uploadImageContent(fileDto);
        }

        return uploadContentPath;
    }

    private String uploadImageContent(FileDto fileDto) throws IOException {
        String uploadPath = null;

        String filename = fileDto.makeRandomFileName();
        String content = Base64.encodeBase64String(fileDto.getFile().getBytes());
        String commitMessage = "Upload image from server";

        Call<GitHubResponse> call = github.createFileContent(token, user, imageRepo, imagePath, filename,
                new HashMap<String, Object>() {{
                    put("content", content);
                    put("message", commitMessage);
                }});

        Response<GitHubResponse> execute = call.execute();

        if(execute.isSuccessful()) {
            GitHubResponse response = execute.body();

            uploadPath = response.getContent().getPath();
            String successMessage = "Path: " + response.getContent().getPath()
                    + "\n"
                    + "Sha: " + response.getCommit().getSha();
            this.HLForCreateContentAPI(adminUser, successMessage, execute.isSuccessful());
        }
        else {
            GitHubErrorResponse errorResponse = gson.fromJson(execute.errorBody().string(), (Type) GitHubErrorResponse.class);

            String errorMessage = errorResponse.getMessage().replace("\n", " ")
                    + "\n"
                    + errorResponse.getDocumentation_url();
            this.HLForCreateContentAPI(adminUser, errorMessage, execute.isSuccessful());
        }

        return uploadPath;
    }

    /**
     * [Note]
     * 1. HL == HistoryLog
     * */
    private void HLForCreateContentAPI(User worker, String message, boolean isSuccessful) {
        String title, content;
        title = isSuccessful ? "Successful " : "Failed ";
        title += "GitHub API - create content";
        content = "Message: " + message;

        historyLogService.writeHistoryLog(HistoryLogAction.API, worker, title, content, isSuccessful);
    }
}
