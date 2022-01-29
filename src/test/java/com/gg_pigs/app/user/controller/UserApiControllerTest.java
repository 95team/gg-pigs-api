package com.gg_pigs.app.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gg_pigs.app.user.dto.CreateDtoUser;
import com.gg_pigs.app.user.dto.RetrieveDtoUser;
import com.gg_pigs.app.user.dto.UpdateDtoUser;
import com.gg_pigs.app.user.service.UserService;
import com.gg_pigs._common.SecuritySetUp4ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserApiController.class)
class UserApiControllerTest extends SecuritySetUp4ControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    private final CreateDtoUser createDtoUser = new CreateDtoUser();
    private final RetrieveDtoUser retrieveDtoUser = new RetrieveDtoUser();
    private final UpdateDtoUser updateDtoUser = new UpdateDtoUser();

    @DisplayName("[테스트] create() :: authenticated 권한이 필요합니다.")
    @Test
    public void Test_create() throws Exception {
        // Given
        String content = objectMapper.writeValueAsString(createDtoUser);
        MockHttpServletRequestBuilder mockRequest = post("/api/v1/users").contentType(MediaType.APPLICATION_JSON)
                                                                         .accept(MediaType.APPLICATION_JSON)
                                                                         .content(content);

        // When // Then
        mockMvc.perform(mockRequest)
               .andExpect(status().isOk())
               .andDo(print());
    }

    @DisplayName("[테스트] read() :: authenticated 권한 필요합니다.")
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

    @DisplayName("[테스트] readAll() :: authenticated 권한 필요합니다.")
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

    @DisplayName("[테스트] update() :: ADMIN / USER 권한 필요합니다.")
    @Test
    public void Test_update() throws Exception {
        // Given
        // 1. 권한(ROLE_USER) 설정합니다.
        setUpUserRole();

        // 2. request 데이터 설정합니다.
        String content = objectMapper.writeValueAsString(updateDtoUser);
        MockHttpServletRequestBuilder mockRequest = put("/api/v1/users/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);

        // When // Then
        mockMvc.perform(mockRequest)
               .andExpect(status().isOk())
               .andDo(print());
    }

    @DisplayName("[테스트] delete() :: ADMIN 권한 필요합니다.")
    @Test
    public void Test_delete() throws Exception {
        //Given
        // 1. 권한(ROLE_USER) 설정합니다.
        setUpAdminRole();

        // When // Then
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(delete("/api/v1/users/1"))
                                                                 .andExpect(status().isOk())
                                                                 .andDo(print())
                                                                 .andReturn().getResponse();

        System.out.println(mockHttpServletResponse);
    }
}