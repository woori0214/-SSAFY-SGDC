package com.ssafy.sgdc.exception;

import java.io.Serial;

public class CustomApiException extends RuntimeException {

    @Serial
    private static final int serialVersionUID=1;


    public CustomApiException(String message) {
        super(message);
    }
}