package com.ssafy.sgdc.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ssafy.sgdc.util.response.Code;
import com.ssafy.sgdc.util.response.CustomException;
import com.ssafy.sgdc.util.response.GeneralResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    ObjectMapper ob = new ObjectMapper().registerModule(new JavaTimeModule());

    // 유효한 자격증명을 제공하지 않고 접근하려 할 때 401 error 리턴
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException , RuntimeException{
        Code code = (Code) request.getAttribute("errorCode");

        String json = ob.writeValueAsString(GeneralResponse.toResponseEntity(
                code
        ));
        response.setStatus(code.getStatus().value());
        response.getWriter().write(json);
        response.flushBuffer();
    }
}