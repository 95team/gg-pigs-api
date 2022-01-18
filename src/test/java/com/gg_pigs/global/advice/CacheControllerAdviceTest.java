package com.gg_pigs.global.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gg_pigs.app.poster.controller.PosterApiController;
import com.gg_pigs.app.poster.dto.PosterDto;
import com.gg_pigs.app.poster.service.PosterService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.gg_pigs.global.CommonDefinition.DEFAULT_CACHE_MAX_AGE;
import static com.gg_pigs.global.CommonDefinition.ZERO_CACHE_MAX_AGE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@WebMvcTest(value = PosterApiController.class)
class CacheControllerAdviceTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean PosterService posterService;

    private PosterDto.Create.RequestDto createRequestDto;
    private PosterDto.Update.RequestDto updateRequestDto;

    private Long mockId = 1L;
    private String mockTitle = "This is a title.";
    private String mockUserEmail = "test@email.com";
    private String mockDescription = "This is a detail description.";
    private String mockKeywords = "This is a keywords.";
    private String mockSlug = "This-is-a-title";
    private String mockPosterType = "R1";
    private String mockImagePath = "/src/image/exmaple.jpg";
    private String mockSiteUrl = "siteUrl";
    private String mockRowPosition = "1";
    private String mockColumnPosition = "1";
    private String mockStartedDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    private String mockFinishedDate = LocalDate.now().plusMonths(1).format(DateTimeFormatter.ISO_LOCAL_DATE);

    @BeforeEach
    void setUp() {
        createRequestDto = PosterDto.Create.RequestDto.builder().build();
        updateRequestDto = PosterDto.Update.RequestDto.builder().build();
    }

    @Test
    public void When_call_GET_method_Then_set_cache() throws Exception {
        // Given
        String targetCacheControl = "max-age=" +  DEFAULT_CACHE_MAX_AGE;

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/api/v2/posters"))
                .andExpect(header().exists(HttpHeaders.CACHE_CONTROL))
                .andReturn().getResponse();

        // Then
        String cacheControl = response.getHeader(HttpHeaders.CACHE_CONTROL);

        Assertions.assertThat(cacheControl.contains(targetCacheControl)).isTrue();
    }

    @Test
    public void When_call_POST_method_Then_set_cache_zero() throws Exception {
        // Given
        String targetCacheControl = "max-age=" +  ZERO_CACHE_MAX_AGE;

        // When
        String content = objectMapper.writeValueAsString(createRequestDto);

        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/posters")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(header().exists(HttpHeaders.CACHE_CONTROL))
                .andReturn().getResponse();

        // Then
        String cacheControl = response.getHeader(HttpHeaders.CACHE_CONTROL);

        Assertions.assertThat(cacheControl.contains(targetCacheControl)).isTrue();
    }

    @Test
    public void When_call_PUT_method_Then_set_cache_zero() throws Exception {
        // Given
        String targetCacheControl = "max-age=" +  ZERO_CACHE_MAX_AGE;

        // When
        String content = objectMapper.writeValueAsString(updateRequestDto);

        MockHttpServletResponse response = mockMvc.perform(put("/api/v1/posters/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(header().exists(HttpHeaders.CACHE_CONTROL))
                .andReturn().getResponse();

        // Then
        String cacheControl = response.getHeader(HttpHeaders.CACHE_CONTROL);

        Assertions.assertThat(cacheControl.contains(targetCacheControl)).isTrue();
    }

    @Test
    public void When_call_DELETE_method_Then_set_cache_zero() throws Exception {
        // Given
        String targetCacheControl = "max-age=" +  ZERO_CACHE_MAX_AGE;

        // When
        MockHttpServletResponse response = mockMvc.perform(delete("/api/v1/posters/1"))
                .andExpect(header().exists(HttpHeaders.CACHE_CONTROL))
                .andReturn().getResponse();

        // Then
        String cacheControl = response.getHeader(HttpHeaders.CACHE_CONTROL);

        Assertions.assertThat(cacheControl.contains(targetCacheControl)).isTrue();
    }
}