package com.gg_pigs.poster.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gg_pigs.poster.dto.CreateDtoPoster;
import com.gg_pigs.poster.dto.RetrieveDtoPoster;
import com.gg_pigs.poster.dto.UpdateDtoPoster;
import com.gg_pigs.poster.service.PosterService;
import org.junit.jupiter.api.BeforeEach;
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
    private RetrieveDtoPoster retrieveDtoPoster;
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
        retrieveDtoPoster = new RetrieveDtoPoster(mockId, mockUserEmail, mockTitle, mockDescription, mockKeywords, mockSlug, mockPosterType, "300", "250", mockImagePath, mockSiteUrl, mockRowPosition, mockColumnPosition, 'Y', mockStartedDate, mockFinishedDate);
        updateDtoPoster = new UpdateDtoPoster(mockId, mockUserEmail, mockTitle, mockDescription, mockKeywords, mockPosterType, mockImagePath, mockSiteUrl, mockRowPosition, mockColumnPosition, 'Y', mockStartedDate, mockFinishedDate);

        Mockito.when(posterService.retrievePoster(any(Long.class))).thenReturn(retrieveDtoPoster);
    }

    @Test
    public void poster_한건_생성() throws Exception {
        String content = objectMapper.writeValueAsString(createDtoPoster);

        mockMvc.perform(post("/api/v1/posters")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void poster_한건_조회() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(get("/api/v1/posters/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isMap())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse.getContentAsString());
    }

    @Test
    public void poster_전체_조회() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(get("/api/v1/posters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse.getContentAsString());
    }

    @Test
    public void poster_전체_조회_v2() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(get("/api/v2/posters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse.getContentAsString());
    }

    @Test
    public void poster_한건_업데이트() throws Exception {
        String content = objectMapper.writeValueAsString(updateDtoPoster);

        mockMvc.perform(put("/api/v1/posters/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void poster_한건_삭제() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(delete("/api/v1/posters/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse.getContentAsString());
    }
}