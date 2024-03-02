package com.ssafy.sgdc.util.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<GeneralResponse> handleException (CustomException e){
        return GeneralResponse.toResponseEntity(e.getCode());
    }
}