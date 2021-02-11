package com.gg_pigs.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gg_pigs._common.utility.JwtProvider;
import com.gg_pigs.user.dto.CreateDtoUser;
import com.gg_pigs.user.dto.RetrieveDtoUser;
import com.gg_pigs.user.dto.UpdateDtoUser;
import com.gg_pigs.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserApiController.class)
class UserApiControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean JwtProvider jwtProvider;
    @MockBean UserService userService;

    private CreateDtoUser createDtoUser = new CreateDtoUser();
    private RetrieveDtoUser retrieveDtoUser = new RetrieveDtoUser();
    private UpdateDtoUser updateDtoUser = new UpdateDtoUser();

    @Test
    public void user_한건_생성() throws Exception {
        // Given
        String content = objectMapper.writeValueAsString(createDtoUser);

        // When // Then
        mockMvc.perform(post("/api/v1/users").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void user_한건_조회() throws Exception {
        // Given
        Mockito.when(userService.retrieveOneUser(anyLong())).thenReturn(retrieveDtoUser);

        // When // Then
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isMap())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse.getContentAsString());
    }

    @Test
    public void user_전체_조회() throws Exception {
        // Given
        Mockito.when(userService.retrieveOneUser(anyLong())).thenReturn(retrieveDtoUser);

        // When // Then
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse.getContentAsString());
    }

    @Test
    public void user_한건_업데이트() throws Exception {
        // Given
        String content = objectMapper.writeValueAsString(updateDtoUser);

        // When // Then
        mockMvc.perform(put("/api/v1/users/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void user_한건_삭제() throws Exception {
        //Given // When // Then
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse);
    }
}