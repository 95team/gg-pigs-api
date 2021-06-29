package com.gg_pigs.posterRequest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gg_pigs._common.enums.PosterReviewStatus;
import com.gg_pigs._common.enums.UserRole;
import com.gg_pigs._common.utility.JwtProvider;
import com.gg_pigs.posterRequest.dto.CreateDtoPosterRequest;
import com.gg_pigs.posterRequest.dto.ReadDtoPosterRequest;
import com.gg_pigs.posterRequest.dto.UpdateDtoPosterRequest;
import com.gg_pigs.posterRequest.service.PosterRequestService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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

@WebMvcTest(value = PosterRequestApiController.class)
class PosterRequestApiControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean JwtProvider jwtProvider;
    @MockBean PosterRequestService posterRequestService;

    @Mock Claims claims;

    private Long mockId = 1L;
    private String mockTitle = "This is a title.";
    private String mockUserEmail = "test@email.com";
    private String mockDescription = "This is a detail description.";
    private String mockKeywords = "This is a keywords.";
    private String mockPosterRequestType = "R1";
    private String mockImagePath = "/src/image/exmaple.jpg";
    private String mockSiteUrl = "siteUrl";
    private String mockRowPosition = "1";
    private String mockColumnPosition = "1";
    private String mockStartedDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    private String mockFinishedDate = LocalDate.now().plusMonths(1).format(DateTimeFormatter.ISO_LOCAL_DATE);

    private CreateDtoPosterRequest createDtoPosterRequest = new CreateDtoPosterRequest(mockTitle, mockUserEmail, mockDescription, mockKeywords, mockPosterRequestType, mockImagePath, mockSiteUrl, mockRowPosition, mockColumnPosition, mockStartedDate, mockFinishedDate);
    private ReadDtoPosterRequest readDtoPosterRequest = new ReadDtoPosterRequest(mockId, mockUserEmail, mockTitle, mockDescription, mockKeywords, mockPosterRequestType, "300", "300", mockImagePath, mockSiteUrl, mockRowPosition, mockColumnPosition, PosterReviewStatus.NEW, mockStartedDate, mockFinishedDate);
    private UpdateDtoPosterRequest updateDtoPosterRequest = new UpdateDtoPosterRequest(mockId, mockUserEmail, mockTitle, mockDescription, mockKeywords, mockPosterRequestType, mockImagePath, mockSiteUrl, mockRowPosition, mockColumnPosition, PosterReviewStatus.NEW.name(), mockStartedDate, mockFinishedDate);

    @BeforeEach
    void setUp() {
        Mockito.when(posterRequestService.readPosterRequest(any(Long.class))).thenReturn(readDtoPosterRequest);
        Mockito.when(posterRequestService.isPossibleSeat(any(), anyLong(), anyLong(), anyString())).thenReturn(true);
    }

    @DisplayName("[테스트] createPosterRequest()")
    @Test
    public void Test_createPosterRequest() throws Exception {
        // Given
        String content = objectMapper.writeValueAsString(createDtoPosterRequest);

        List<String[]> allPossibleSeats = new ArrayList<>();
        allPossibleSeats.add(new String[]{mockRowPosition, mockColumnPosition});
        Mockito.when(posterRequestService.getAllPossibleSeats(any())).thenReturn(allPossibleSeats);

        // When // Then
        mockMvc.perform(post("/api/v1/poster-requests")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("[테스트] readPosterRequest()")
    @Test
    public void Test_readPosterRequest() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(get("/api/v1/poster-requests/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isMap())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse.getContentAsString());
    }

    @DisplayName("[테스트] readPosterRequests() (v1)")
    @Test
    public void Test_readPosterRequests_v1() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(get("/api/v1/poster-requests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse.getContentAsString());
    }

    @DisplayName("[테스트] readPosterRequests() (v2)")
    @Test
    public void Test_readPosterRequests_v2() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(get("/api/v2/poster-requests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse.getContentAsString());
    }

    @DisplayName("[테스트] updatePosterRequest()")
    @Test
    public void Test_updatePosterRequest() throws Exception {
        // Given
        String content = objectMapper.writeValueAsString(updateDtoPosterRequest);

        Cookie cookie = new Cookie("jwt", "cookie");
        String role = String.valueOf(UserRole.ROLE_ADMIN);

        Mockito.when(jwtProvider.getPayloadFromToken(any())).thenReturn(claims);
        Mockito.when(claims.getAudience()).thenReturn(mockUserEmail);
        Mockito.when(claims.get("role")).thenReturn(role);

        // When // Then
        mockMvc.perform(put("/api/v1/poster-requests/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("work", "review")
                .content(content)
                .cookie(cookie))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("[테스트] deletePosterRequest()")
    @Test
    public void Test_deletePosterRequest() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(delete("/api/v1/poster-requests/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse.getContentAsString());
    }
}