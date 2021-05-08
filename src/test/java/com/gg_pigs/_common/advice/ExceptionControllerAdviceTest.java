package com.gg_pigs._common.advice;

import com.gg_pigs._common.dto.ApiResponse;
import com.gg_pigs._common.exception.BadRequestException;
import com.gg_pigs._common.exception.LoginFailureException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.naming.LimitExceededException;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;

class ExceptionControllerAdviceTest {

    ExceptionControllerAdvice controllerAdvice = new ExceptionControllerAdvice();

    String message = "This is a exception message.";
    StackTraceElement[] stackTraces = {new StackTraceElement("Class", "Method", "FileName", 11)};

    @DisplayName("[테스트] handleMissingRequestCookieException()")
    @Test
    void Test_handleMissingRequestCookieException() {
        // Given
        String expectedData = "적절하지 않은 요청입니다. (Please check the cookie 'null')";

        MissingRequestCookieException mockException = Mockito.mock(MissingRequestCookieException.class);
        Mockito.when(mockException.getMessage()).thenReturn(message);
        Mockito.when(mockException.getStackTrace()).thenReturn(stackTraces);

        // When
        ResponseEntity<ApiResponse> responseEntity = controllerAdvice.handleMissingRequestCookieException(mockException);

        // Then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(responseEntity.getBody().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(responseEntity.getBody().getMessage()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        Assertions.assertThat(responseEntity.getBody().getData()).isEqualTo(expectedData);
    }

    @DisplayName("[테스트] handleLoginFailureException()")
    @Test
    void Test_handleLoginFailureException() {
        // Given
        String expectedData = message;

        LoginFailureException mockException = Mockito.mock(LoginFailureException.class);
        Mockito.when(mockException.getMessage()).thenReturn(message);
        Mockito.when(mockException.getStackTrace()).thenReturn(stackTraces);

        // When
        ResponseEntity<ApiResponse> responseEntity = controllerAdvice.handleLoginFailureException(mockException);

        // Then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(responseEntity.getBody().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(responseEntity.getBody().getMessage()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        Assertions.assertThat(responseEntity.getBody().getData()).isEqualTo(expectedData);
    }

    @DisplayName("[테스트] handleBadRequestException()")
    @Test
    void handleBadRequestException() {
        // Given
        String expectedData = message;

        BadRequestException mockException = Mockito.mock(BadRequestException.class);
        Mockito.when(mockException.getMessage()).thenReturn(message);
        Mockito.when(mockException.getStackTrace()).thenReturn(stackTraces);

        // When
        ResponseEntity<ApiResponse> responseEntity = controllerAdvice.handleBadRequestException(mockException);

        // Then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(responseEntity.getBody().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(responseEntity.getBody().getMessage()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        Assertions.assertThat(responseEntity.getBody().getData()).isEqualTo(expectedData);
    }

    @DisplayName("[테스트] handleLimitExceededException()")
    @Test
    void handleLimitExceededException() {
        // Given
        String expectedData = message;

        LimitExceededException mockException = Mockito.mock(LimitExceededException.class);
        Mockito.when(mockException.getMessage()).thenReturn(message);
        Mockito.when(mockException.getStackTrace()).thenReturn(stackTraces);

        // When
        ResponseEntity<ApiResponse> responseEntity = controllerAdvice.handleLimitExceededException(mockException);

        // Then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(responseEntity.getBody().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(responseEntity.getBody().getMessage()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        Assertions.assertThat(responseEntity.getBody().getData()).isEqualTo(expectedData);
    }

    @DisplayName("[테스트] handleMissingServletRequestParameterException()")
    @Test
    void handleMissingServletRequestParameterException() {
        // Given
        String expectedData = message;

        MissingServletRequestParameterException mockException = Mockito.mock(MissingServletRequestParameterException.class);
        Mockito.when(mockException.getMessage()).thenReturn(message);
        Mockito.when(mockException.getStackTrace()).thenReturn(stackTraces);

        // When
        ResponseEntity<ApiResponse> responseEntity = controllerAdvice.handleMissingServletRequestParameterException(mockException);

        // Then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(responseEntity.getBody().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(responseEntity.getBody().getMessage()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        Assertions.assertThat(responseEntity.getBody().getData()).isEqualTo(expectedData);
    }

    @DisplayName("[테스트] handleIOException()")
    @Test
    void handleIOException() {
        // Given
        String expectedData = message;

        IOException mockException = Mockito.mock(IOException.class);
        Mockito.when(mockException.getMessage()).thenReturn(message);
        Mockito.when(mockException.getStackTrace()).thenReturn(stackTraces);

        // When
        ResponseEntity<ApiResponse> responseEntity = controllerAdvice.handleIOException(mockException);

        // Then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        Assertions.assertThat(responseEntity.getBody().getCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        Assertions.assertThat(responseEntity.getBody().getMessage()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        Assertions.assertThat(responseEntity.getBody().getData()).isEqualTo(expectedData);
    }

    @DisplayName("[테스트] handleEntityNotFoundException()")
    @Test
    void handleEntityNotFoundException() {
        // Given
        String expectedData = message;

        EntityNotFoundException mockException = Mockito.mock(EntityNotFoundException.class);
        Mockito.when(mockException.getMessage()).thenReturn(message);
        Mockito.when(mockException.getStackTrace()).thenReturn(stackTraces);

        // When
        ResponseEntity<ApiResponse> responseEntity = controllerAdvice.handleEntityNotFoundException(mockException);

        // Then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(responseEntity.getBody().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(responseEntity.getBody().getMessage()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        Assertions.assertThat(responseEntity.getBody().getData()).isEqualTo(expectedData);
    }

    @DisplayName("[테스트] handleIllegalArgumentException()")
    @Test
    void handleIllegalArgumentException() {
        // Given
        String expectedData = message;

        IllegalArgumentException mockException = Mockito.mock(IllegalArgumentException.class);
        Mockito.when(mockException.getMessage()).thenReturn(message);
        Mockito.when(mockException.getStackTrace()).thenReturn(stackTraces);

        // When
        ResponseEntity<ApiResponse> responseEntity = controllerAdvice.handleIllegalArgumentException(mockException);

        // Then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(responseEntity.getBody().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(responseEntity.getBody().getMessage()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        Assertions.assertThat(responseEntity.getBody().getData()).isEqualTo(expectedData);
    }

    @DisplayName("[테스트] handleDataIntegrityViolationException()")
    @Test
    void handleDataIntegrityViolationException() {
        // Given
        String expectedData = message;

        DataIntegrityViolationException mockException = Mockito.mock(DataIntegrityViolationException.class);
        Mockito.when(mockException.getMessage()).thenReturn(message);
        Mockito.when(mockException.getStackTrace()).thenReturn(stackTraces);

        // When
        ResponseEntity<ApiResponse> responseEntity = controllerAdvice.handleDataIntegrityViolationException(mockException);

        // Then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(responseEntity.getBody().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(responseEntity.getBody().getMessage()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        Assertions.assertThat(responseEntity.getBody().getData()).isEqualTo(expectedData);
    }

    @DisplayName("[테스트] handleInternalException()")
    @Test
    void handleInternalException() {
        // Given
        String expectedData = message;

        InternalException mockException = Mockito.mock(InternalException.class);
        Mockito.when(mockException.getMessage()).thenReturn(message);
        Mockito.when(mockException.getStackTrace()).thenReturn(stackTraces);

        // When
        ResponseEntity<ApiResponse> responseEntity = controllerAdvice.handleInternalException(mockException);

        // Then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        Assertions.assertThat(responseEntity.getBody().getCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        Assertions.assertThat(responseEntity.getBody().getMessage()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        Assertions.assertThat(responseEntity.getBody().getData()).isEqualTo(expectedData);
    }

    @DisplayName("[테스트] handleException()")
    @Test
    void handleException() {
        // Given
        String expectedData = message;

        Exception mockException = Mockito.mock(Exception.class);
        Mockito.when(mockException.getMessage()).thenReturn(message);
        Mockito.when(mockException.getStackTrace()).thenReturn(stackTraces);

        // When
        ResponseEntity<ApiResponse> responseEntity = controllerAdvice.handleException(mockException);

        // Then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        Assertions.assertThat(responseEntity.getBody().getCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        Assertions.assertThat(responseEntity.getBody().getMessage()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        Assertions.assertThat(responseEntity.getBody().getData()).isEqualTo(expectedData);
    }
}