package com.gg_pigs.app.poster.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gg_pigs.app.poster.dto.CreateDtoPoster;
import com.gg_pigs.app.poster.dto.ReadDtoPoster;
import com.gg_pigs.app.poster.dto.UpdateDtoPoster;
import com.gg_pigs.app.poster.service.PosterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * [References]
 * 1. https://www.baeldung.com/spring-boot-testing
 * */

@WebMvcTest(value = PosterApiController.class)
class PosterApiControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean PosterService posterService;

    private CreateDtoPoster createDtoPoster;
    private ReadDtoPoster readDtoPoster;
    private UpdateDtoPoster updateDtoPoster;

    @BeforeEach
    void setUp() {
        Long mockId = 1L;
        String mockTitle = "This is a title.";
        String mockUserEmail = "test@email.com";
        String mockDescription = "This is a detail description.";
        String mockKeywords = "This is a keywords.";
        String mockSlug = "This-is-a-title";
        String mockPosterType = "R1";
        String mockImagePath = "/src/image/exmaple.jpg";
        String mockSiteUrl = "siteUrl";
        String mockRowPosition = "1";
        String mockColumnPosition = "1";
        String mockStartedDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String mockFinishedDate = LocalDate.now().plusMonths(1).format(DateTimeFormatter.ISO_LOCAL_DATE);

        createDtoPoster = new CreateDtoPoster(mockTitle, mockUserEmail, mockDescription, mockKeywords, mockPosterType, mockImagePath, mockSiteUrl, mockRowPosition, mockColumnPosition, mockStartedDate, mockFinishedDate);
        readDtoPoster = new ReadDtoPoster(mockId, mockUserEmail, mockTitle, mockDescription, mockKeywords, mockSlug, mockPosterType, "300", "250", mockImagePath, mockSiteUrl, mockRowPosition, mockColumnPosition, 'Y', mockStartedDate, mockFinishedDate);
        updateDtoPoster = new UpdateDtoPoster(mockId, mockUserEmail, mockTitle, mockDescription, mockKeywords, mockPosterType, mockImagePath, mockSiteUrl, mockRowPosition, mockColumnPosition, 'Y', mockStartedDate, mockFinishedDate);

        Mockito.when(posterService.readPoster(any(Long.class))).thenReturn(readDtoPoster);
    }

    @DisplayName("[테스트] create() : Poster 생성")
    @Test
    public void Test_create() throws Exception {
        String content = objectMapper.writeValueAsString(createDtoPoster);

        mockMvc.perform(post("/api/v1/posters")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("[테스트] read() : Poster 조회")
    @Test
    public void Test_read() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(get("/api/v1/posters/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isMap())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse.getContentAsString());
    }

    @DisplayName("[테스트] readAll() : Poster 리스트 조회 (v1)")
    @Test
    public void Test_readAll_v1() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(get("/api/v1/posters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse.getContentAsString());
    }

    @DisplayName("[테스트] readAll() : Poster 리스트 조회 (v2)")
    @Test
    public void Test_readAll_v2() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(get("/api/v2/posters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse.getContentAsString());
    }

    @DisplayName("[테스트] update() : Poster 수정")
    @Test
    public void Test_update() throws Exception {
        String content = objectMapper.writeValueAsString(updateDtoPoster);

        mockMvc.perform(put("/api/v1/posters/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("[테스트] delete() : Poster 삭제")
    @Test
    public void Test_delete() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(delete("/api/v1/posters/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse.getContentAsString());
    }
}