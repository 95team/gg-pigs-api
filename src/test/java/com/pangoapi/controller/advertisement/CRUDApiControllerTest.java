package com.pangoapi.controller.advertisement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pangoapi.dto.advertisement.CreateDtoAdvertisement;
import com.pangoapi.dto.advertisement.RetrieveDtoAdvertisement;
import com.pangoapi.dto.advertisement.UpdateDtoAdvertisement;
import com.pangoapi.service.advertisement.AdvertisementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

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

@WebMvcTest(value = CRUDApiController.class)
class CRUDApiControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean AdvertisementService advertisementService;

    private CreateDtoAdvertisement createDtoAdvertisement;
    private RetrieveDtoAdvertisement retrieveDtoAdvertisement;
    private UpdateDtoAdvertisement updateDtoAdvertisement;

    @BeforeEach
    void setUp() {
        createDtoAdvertisement = new CreateDtoAdvertisement("title", "userEmail", "detailDescription", "R1", "imagePath", "siteUrl", "1", "1");
        retrieveDtoAdvertisement = new RetrieveDtoAdvertisement(1L, "user@email.com", "title", "detailDescription", "R1", "300", "250", "imagePath", "siteUrl", "1", "1", 'Y');
        updateDtoAdvertisement = new UpdateDtoAdvertisement(1L, "user@email.com", "title", "detailDescription", "R1", "imagePath", "siteUrl","1", "1", 'Y');

        Mockito.when(advertisementService.createOneAdvertisement(any(CreateDtoAdvertisement.class))).thenReturn(1L);
        Mockito.when(advertisementService.retrieveOneAdvertisement(any(Long.class))).thenReturn(retrieveDtoAdvertisement);
        Mockito.when(advertisementService.retrieveAllAdvertisement()).thenReturn(new ArrayList<>(Arrays.asList(retrieveDtoAdvertisement)));
        Mockito.when(advertisementService.updateOneAdvertisement(any(Long.class), any(UpdateDtoAdvertisement.class))).thenReturn(1L);
    }

    @Test
    public void advertisement_한건_생성() throws Exception {
        String content = objectMapper.writeValueAsString(createDtoAdvertisement);

        mockMvc.perform(post("/api/v1/advertisements")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void advertisement_한건_조회() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(get("/api/v1/advertisements/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isMap())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse.getContentAsString());
    }

    @Test
    public void advertisement_전체_조회() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(get("/api/v1/advertisements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse.getContentAsString());
    }

    @Test
    public void advertisement_한건_업데이트() throws Exception {
        String content = objectMapper.writeValueAsString(updateDtoAdvertisement);

        mockMvc.perform(put("/api/v1/advertisements/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void advertisement_한건_삭제() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(delete("/api/v1/advertisements/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse.getContentAsString());
    }
}