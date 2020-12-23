package com.pangoapi.advertisement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pangoapi.advertisement.controller.AdvertisementApiController;
import com.pangoapi.advertisement.dto.CreateDtoAdvertisement;
import com.pangoapi.advertisement.dto.RetrieveDtoAdvertisement;
import com.pangoapi.advertisement.dto.UpdateDtoAdvertisement;
import com.pangoapi.advertisement.service.AdvertisementService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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

@WebMvcTest(value = AdvertisementApiController.class)
class AdvertisementApiControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean AdvertisementService advertisementService;

    private CreateDtoAdvertisement createDtoAdvertisement;
    private RetrieveDtoAdvertisement retrieveDtoAdvertisement;
    private UpdateDtoAdvertisement updateDtoAdvertisement;

    @BeforeEach
    void setUp() throws Exception {
        Long mockId = 1L;
        String mockTitle = "title";
        String mockUserEmail = "user@email.com";
        String mockDetailDescription = "This is a detail description.";
        String mockKeywords = "This is a keywords.";
        String mockAdvertisemenType = "R1";
        String mockImagePath = "/src/image/exmaple.jpg";
        String mockSiteUrl = "siteUrl";
        String mockRowPosition = "1";
        String mockColumnPosition = "1";
        String mockStartedDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String mockFinishedDate = LocalDate.now().plusMonths(1).format(DateTimeFormatter.ISO_LOCAL_DATE);

        createDtoAdvertisement = new CreateDtoAdvertisement(mockTitle, mockUserEmail, mockDetailDescription, mockKeywords, mockAdvertisemenType, mockImagePath, mockSiteUrl, mockRowPosition, mockColumnPosition, mockStartedDate, mockFinishedDate);
        retrieveDtoAdvertisement = new RetrieveDtoAdvertisement(mockId, mockUserEmail, mockTitle, mockDetailDescription, mockKeywords, mockAdvertisemenType, "300", "250", mockImagePath, mockSiteUrl, mockRowPosition, mockColumnPosition, 'Y', mockStartedDate, mockFinishedDate);
        updateDtoAdvertisement = new UpdateDtoAdvertisement(mockId, mockUserEmail, mockTitle, mockDetailDescription, mockKeywords, mockAdvertisemenType, mockImagePath, mockSiteUrl, mockRowPosition, mockColumnPosition, 'Y', mockStartedDate, mockFinishedDate);

        Mockito.when(advertisementService.createOneAdvertisement(any(CreateDtoAdvertisement.class))).thenReturn(mockId);
        Mockito.when(advertisementService.retrieveOneAdvertisement(any(Long.class))).thenReturn(retrieveDtoAdvertisement);
        Mockito.when(advertisementService.retrieveAllAdvertisement(any(HashMap.class))).thenReturn(new ArrayList<>(Arrays.asList(retrieveDtoAdvertisement)));
        Mockito.when(advertisementService.updateOneAdvertisement(any(Long.class), any(UpdateDtoAdvertisement.class))).thenReturn(mockId);
    }

    @Test
    public void advertisement_한건_생성() throws Exception {
        String content = objectMapper.writeValueAsString(createDtoAdvertisement);

        mockMvc.perform(post("/api/v1/posters")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void advertisement_한건_조회() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(get("/api/v1/posters/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isMap())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse.getContentAsString());
    }

    @Test
    public void advertisement_전체_조회() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(get("/api/v1/posters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse.getContentAsString());
    }

    @Test
    public void advertisement_한건_업데이트() throws Exception {
        String content = objectMapper.writeValueAsString(updateDtoAdvertisement);

        mockMvc.perform(put("/api/v1/posters/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void advertisement_한건_삭제() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(delete("/api/v1/posters/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse.getContentAsString());
    }
}