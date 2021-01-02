package com.pangoapi._common.utility.github;

import com.pangoapi.file.dto.FileDto;
import com.pangoapi.historyLog.service.HistoryLogService;
import com.pangoapi.user.dto.CreateDtoUser;
import com.pangoapi.user.entity.User;
import com.pangoapi.user.repository.UserRepository;
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
        File sampleFile = new ClassPathResource("/files/logo.png").getFile();
        MultipartFile sampleMultipartFile = new MockMultipartFile("logo.png", "logo.png", "image/*", new FileInputStream(sampleFile));
        FileDto fileDto = new FileDto(sampleMultipartFile, "image", "poster");

        // When
        String uploadImageUrl = githubClient.uploadContent(fileDto);

        // Then
        Assertions.assertThat(uploadImageUrl).isNotNull();
    }
}