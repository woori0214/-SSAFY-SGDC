package com.ssafy.sgdc.util.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
public class GeneralResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String message;
    private final Map<String, String> data;

    public static ResponseEntity<GeneralResponse> toResponseEntity(Code code){
        return ResponseEntity.status(code.getStatus()).body(
                GeneralResponse.builder()
                        .status(code.getStatus().value())
                        .message(code.getMessage())
                        .data(code.getBody()).build()
        );
    }
}