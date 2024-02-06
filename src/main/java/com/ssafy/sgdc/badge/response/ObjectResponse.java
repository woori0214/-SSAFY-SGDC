package com.ssafy.sgdc.badge.response;

import com.ssafy.sgdc.util.response.GeneralResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class ObjectResponse implements Serializable {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String message;
    private final Map<String, Object> data;

}
