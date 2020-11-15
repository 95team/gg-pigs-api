package com.pangoapi._common.exception;

import com.pangoapi._common.dto.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@ControllerAdvice
public class CommonExceptionHandler {

    private void printCommonExceptionHandlerMessage(Exception e) {
        System.out.println(
                this.getClass() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "\n\t"
                + e.getClass() + ": " + e.getMessage() + "(" + e.getStackTrace()[0].toString() + ")"
        );
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
     * 예시 : @RequestParam("image") FileDto fileDto
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
     *  예시 : Repository.save() 등 (Request DTO 의 누락된 데이터, Null 값 캐치 등)
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

        ApiResponse response = ApiResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
