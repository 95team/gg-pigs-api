package com.pangoapi._common.utility.github;

import com.pangoapi._common.utility.github.dto.response.GitHubResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.HashMap;

public interface GitHub {

    @Headers({
            "Accept: application/vnd.github.v3+json",
    })
    @PUT("repos/{user}/{repo}/contents/{path}/{file}")
    Call<GitHubResponse> createFileContent(@Header("Authorization") String token,
                                           @Path("user") String user,
                                           @Path("repo") String repo,
                                           @Path("path") String path,
                                           @Path("file") String file,
                                           @Body() HashMap<String, Object> content);

}
