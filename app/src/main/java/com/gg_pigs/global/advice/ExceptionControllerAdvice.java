package com.gg_pigs.global.advice;

import com.gg_pigs.global.dto.ApiResponse;
import com.gg_pigs.global.exception.GPBadRequestException;
import com.gg_pigs.global.exception.GPLoginFailureException;
import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.LimitExceededException;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;

/**
 * [References]
 * 1. https://cheese10yun.github.io/spring-guide-exception/
 * 2. https://velog.io/@aidenshin/Spring-Boot-Exception-Controller
 * */

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    private void printCommonExceptionHandlerMessage(Exception e) {
        Sentry.captureException(e);

        System.out.println(
                this.getClass() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "\n\t"
                + e.getClass() + ": " + e.getMessage() + "(" + e.getStackTrace()[0].toString() + ")"
        );
    }

    /**
     * handleMissingRequestCookieException(MissingRequestCookieException.class)
     * 목적 : API 요청에서 필수로 요구되는 쿠키가 없을 경우
     * */
    @ExceptionHandler(MissingRequestCookieException.class)
    protected ResponseEntity<ApiResponse> handleMissingRequestCookieException(MissingRequestCookieException e) {
        printCommonExceptionHandlerMessage(e);

        ApiResponse response = ApiResponse.of(HttpStatus.BAD_REQUEST, "적절하지 않은 요청입니다. (Please check the cookie '" + e.getCookieName() + "')");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * handleLoginFailureException(LoginFailureException.class)
     * 목적 : 로그인에 실패했을 경우
     * */
    @ExceptionHandler(GPLoginFailureException.class)
    protected ResponseEntity<ApiResponse> handleLoginFailureException(GPLoginFailureException e) {
        printCommonExceptionHandlerMessage(e);

        ApiResponse response = ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * handleBadRequestException(BadRequestException.class)
     * 목적 : 잘못된 API 요청인 경우 (포괄적으로 사용할 수 있는 Exception)
     * */
    @ExceptionHandler(GPBadRequestException.class)
    protected ResponseEntity<ApiResponse> handleBadRequestException(GPBadRequestException e) {
        printCommonExceptionHandlerMessage(e);

        ApiResponse response = ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * handleLimitExceededException(LimitExceededException.class)
     * 목적 : API 요청 횟수를 제한한다.
     * */
    @ExceptionHandler(LimitExceededException.class)
    protected ResponseEntity<ApiResponse> handleLimitExceededException(LimitExceededException e) {
        printCommonExceptionHandlerMessage(e);

        ApiResponse response = ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * handleMissingServletRequestParameterException(MissingServletRequestParameterException.class)
     * 목적 : @RequestParam 필수 값인 경우 체크한다.
     * */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ApiResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        printCommonExceptionHandlerMessage(e);

        ApiResponse response = ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    /**
     * handleIOException(IOException.class)
     * 목적 : I/O 와 관련된 에러가 발생했을 경우 (파일 업로드 실패했을 경우, ...)
     * 예시 : image.transferTo(file);
     * */
    @ExceptionHandler(IOException.class)
    protected ResponseEntity<ApiResponse> handleIOException(IOException e) {
        printCommonExceptionHandlerMessage(e);
        log.error(e.getMessage());

        ApiResponse response = ApiResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * handleEntityNotFoundException(EntityNotFoundException.class)
     * 목적 : (찾고자 하는) 데이터를 찾지 못했을 경우, 존재하지 않는 경우
     * 예시 : Advertisement advertisement = advertisementRepository.findById(advertisementId).orElseThrow(() -> new EntityNotFoundException("해당 데이터를 조회할 수 없습니다."));
     * */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ApiResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        printCommonExceptionHandlerMessage(e);

        ApiResponse response = ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     *  handleIllegalArgumentException(IllegalArgumentException.class)
     *  목적 : 유효하지 않은 Argument 가 들어온 경우
     */
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        printCommonExceptionHandlerMessage(e);

        ApiResponse response = ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     *  handleDataIntegrityViolationException(DataIntegrityViolationException.class)
     *  목적 : Hibernate 관련 Exception, ConstraintViolationException, PropertyValueException, DataException(잘못된 sql, data) 포함
     *  예시 : Repository.save() 등 (Request DTO 의 누락된 데이터, Null 값 캐치, SQL 제약 조건 위반(varchar 길이 over) 등)
     * */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<ApiResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        printCommonExceptionHandlerMessage(e);

        ApiResponse response = ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * handleException(Exception.class)
     * 목적 : 명시하지 못한(주로 발생하는 에러 외의) 에러를 핸들링하기 위함
     * */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResponse> handleException(Exception e) {
        printCommonExceptionHandlerMessage(e);
        log.error(e.getMessage());

        ApiResponse response = ApiResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
