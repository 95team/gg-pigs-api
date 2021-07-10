package com.gg_pigs.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gg_pigs.user.dto.CreateDtoUser;
import com.gg_pigs.user.dto.RetrieveDtoUser;
import com.gg_pigs.user.dto.UpdateDtoUser;
import com.gg_pigs.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
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

    @MockBean UserService userService;

    private CreateDtoUser createDtoUser = new CreateDtoUser();
    private RetrieveDtoUser retrieveDtoUser = new RetrieveDtoUser();
    private UpdateDtoUser updateDtoUser = new UpdateDtoUser();

    @DisplayName("[테스트] create()")
    @Test
    public void Test_create() throws Exception {
        // Given
        String content = objectMapper.writeValueAsString(createDtoUser);

        // When // Then
        mockMvc.perform(post("/api/v1/users").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("[테스트] read()")
    @Test
    public void Test_read() throws Exception {
        // Given
        Mockito.when(userService.read(anyLong())).thenReturn(retrieveDtoUser);

        // When // Then
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isMap())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse.getContentAsString());
    }

    @DisplayName("[테스트] readAll()")
    @Test
    public void Test_readAll() throws Exception {
        // Given
        Mockito.when(userService.read(anyLong())).thenReturn(retrieveDtoUser);

        // When // Then
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse.getContentAsString());
    }

    @DisplayName("[테스트] update()")
    @Test
    public void Test_update() throws Exception {
        // Given
        String content = objectMapper.writeValueAsString(updateDtoUser);

        // When // Then
        mockMvc.perform(put("/api/v1/users/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("[테스트] delete()")
    @Test
    public void Test_delete() throws Exception {
        //Given // When // Then
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse();

        System.out.println(mockHttpServletResponse);
    }
}