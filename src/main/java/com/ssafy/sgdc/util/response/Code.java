package com.ssafy.sgdc.util.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum Code {
    INVALID(HttpStatus.BAD_REQUEST, "잘못 된 값이 입력되었습니다.", null),
    INVALID_ID_PW(HttpStatus.BAD_REQUEST, "ID와 PW가 맞지 않습니다.", null),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "token is wrong.", null),
    NULL_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "token is null.", null),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "token was expried.", null);
    private HttpStatus status;
    private String message;
    private Map<String, String> body;
}