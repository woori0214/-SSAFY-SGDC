package com.ssafy.sgdc.exception;

import java.io.Serial;

public class CustomApiException extends RuntimeException {

    @Serial
    private static final long serialVersionUID=1L;


    public CustomApiException(String message) {
        super(message);
    }
}