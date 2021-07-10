package com.gg_pigs._common.utility.github;

import com.gg_pigs.historyLog.service.HistoryLogService;
import com.gg_pigs.user.entity.User;
import com.gg_pigs.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.gg_pigs._common.utility.FileUtil.makeRandomFileName;
import static org.mockito.ArgumentMatchers.anyString;

/**
 * [References]
 * 1. https://www.baeldung.com/spring-classpath-file-access
 * */
@SpringBootTest(
        classes = {
                GitHubClient.class,
        }
)
class GitHubClientTest {

    @Autowired GitHubClient githubClient;

    @MockBean UserRepository userRepository;

    @MockBean HistoryLogService historyLogService;

    @Mock User user;

    @BeforeEach
    void setUp() {
        // Configuration of UserRepository
        Mockito.when(userRepository.findUserByEmail(anyString())).thenReturn(java.util.Optional.of(user));
    }

    @Test
    void When_call_uploadContent_Then_return_uploadPath() throws IOException {
        // Given
        String fileName = "logo.png";
        File sampleFile = new ClassPathResource("/files/logo.png").getFile();
        MultipartFile sampleMultipartFile = new MockMultipartFile("logo.png", "logo.png", "image/*", new FileInputStream(sampleFile));

        // When
        String uploadImageUrl = githubClient.uploadImageContent(sampleMultipartFile, makeRandomFileName(fileName));

        // Then
        Assertions.assertThat(uploadImageUrl).isNotNull();
    }
}