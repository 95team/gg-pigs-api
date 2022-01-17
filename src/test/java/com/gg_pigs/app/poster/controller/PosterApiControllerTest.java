package com.gg_pigs.app.poster.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gg_pigs.app.poster.dto.PosterDto;
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

    private PosterDto.Create.RequestDto createDtoPoster;
    private PosterDto.Read.ResponseDto readDtoPoster;
    private PosterDto.Update.RequestDto updateDtoPoster;

    @BeforeEach
    void setUp() {
        createDtoPoster = PosterDto.Create.RequestDto.builder().build();
        readDtoPoster = PosterDto.Read.ResponseDto.builder().build();
        updateDtoPoster = PosterDto.Update.RequestDto.builder().build();

        Mockito.when(posterService.read(any(Long.class))).thenReturn(readDtoPoster);
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