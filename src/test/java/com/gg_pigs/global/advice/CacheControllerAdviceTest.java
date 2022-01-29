package com.gg_pigs.global.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gg_pigs.app.poster.controller.PosterApiController;
import com.gg_pigs.app.poster.dto.PosterDto;
import com.gg_pigs.app.poster.service.PosterService;
import com.gg_pigs._common.SecuritySetUp4ControllerTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static com.gg_pigs.global.CommonDefinition.DEFAULT_CACHE_MAX_AGE;
import static com.gg_pigs.global.CommonDefinition.ZERO_CACHE_MAX_AGE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = PosterApiController.class)
class CacheControllerAdviceTest extends SecuritySetUp4ControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PosterService posterService;

    @DisplayName("[테스트] POST 호출 시, max-age 0 설정")
    @Test
    public void When_call_POST_method_Then_set_cache_zero() throws Exception {
        // Given
        String targetCacheControl = "max-age=" + ZERO_CACHE_MAX_AGE;
        String content = objectMapper.writeValueAsString(PosterDto.Create.RequestDto.builder().build());

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/api/v1/posters")
                                                                   .contentType(MediaType.APPLICATION_JSON)
                                                                   .accept(MediaType.APPLICATION_JSON)
                                                                   .content(content))
                                                  .andExpect(header().exists(HttpHeaders.CACHE_CONTROL))
                                                  .andExpect(status().isOk())
                                                  .andReturn().getResponse();

        // Then
        String cacheControl = response.getHeader(HttpHeaders.CACHE_CONTROL);

        Assertions.assertThat(cacheControl.contains(targetCacheControl)).isTrue();
    }

    @DisplayName("[테스트] GET 호출 시, max-age 설정(default_cache_max_age)")
    @Test
    public void When_call_GET_method_Then_set_cache() throws Exception {
        // Given
        String targetCacheControl = "max-age=" + DEFAULT_CACHE_MAX_AGE;

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/api/v2/posters"))
                                                  .andExpect(header().exists(HttpHeaders.CACHE_CONTROL))
                                                  .andExpect(status().isOk())
                                                  .andReturn().getResponse();

        // Then
        String cacheControl = response.getHeader(HttpHeaders.CACHE_CONTROL);

        Assertions.assertThat(cacheControl.contains(targetCacheControl)).isTrue();
    }

    @DisplayName("[테스트] PUT 호출 시, max-age 0 설정 (+ USER/ADMIN 권한이 필요합니다.)")
    @Test
    public void When_call_PUT_method_Then_set_cache_zero() throws Exception {
        // Given
        // 1. 권한 설정합니다.
        setUpUserRole();

        // 2. 검증 데이터 설정합니다.
        String targetCacheControl = "max-age=" + ZERO_CACHE_MAX_AGE;
        String content = objectMapper.writeValueAsString(PosterDto.Update.RequestDto.builder().build());

        // When
        MockHttpServletResponse response = mockMvc.perform(put("/api/v1/posters/1")
                                                                   .contentType(MediaType.APPLICATION_JSON)
                                                                   .accept(MediaType.APPLICATION_JSON)
                                                                   .content(content))
                                                  .andExpect(header().exists(HttpHeaders.CACHE_CONTROL))
                                                  .andExpect(status().isOk())
                                                  .andReturn().getResponse();

        // Then
        String cacheControl = response.getHeader(HttpHeaders.CACHE_CONTROL);

        Assertions.assertThat(cacheControl.contains(targetCacheControl)).isTrue();
    }

    @DisplayName("[테스트] DELETE 호출 시, max-age 0 설정 (+ ADMIN 권한이 필요합니다.)")
    @Test
    public void When_call_DELETE_method_Then_set_cache_zero() throws Exception {
        // Given
        // 1. 권한 설정합니다.
        setUpAdminRole();

        // 2. 검증 데이터 설정합니다.
        String targetCacheControl = "max-age=" + ZERO_CACHE_MAX_AGE;

        // When
        MockHttpServletResponse response = mockMvc.perform(delete("/api/v1/posters/1"))
                                                  .andExpect(header().exists(HttpHeaders.CACHE_CONTROL))
                                                  .andExpect(status().isOk())
                                                  .andReturn().getResponse();

        // Then
        String cacheControl = response.getHeader(HttpHeaders.CACHE_CONTROL);
        Assertions.assertThat(cacheControl.contains(targetCacheControl)).isTrue();
    }
}