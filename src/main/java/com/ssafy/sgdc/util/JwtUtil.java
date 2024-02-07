package com.ssafy.sgdc.util;


import com.ssafy.sgdc.user.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtil {
    public final static long ACCESS_TOKEN_VALIDATION = 1000L * 60 * 60; // 30min
    public final static long REFRESH_TOKEN_VALIDATION = 1000L * 60 * 60 * 24; // 24 hour
    private static final String secretKey = "SecretKey";

    public String generateToken(User user, long expiration){ // 토큰 발급
        Map<String, Object> claims = new HashMap<>();
        JwtBuilder jwtBuilder = Jwts.builder()              // header.payload 정보담기
                .setClaims(claims)
                .setSubject(user.getLoginId())              // 제목 : loginId
                .setIssuedAt(new Date(System.currentTimeMillis())) // 현재 시간
                .setExpiration(new Date(System.currentTimeMillis()+ expiration)) // 만료시간
                .signWith(SignatureAlgorithm.HS256, secretKey); // 알고리즘, 암호화 키

        return jwtBuilder.compact();
    }
    public String generateAccessToken(User user){
        return generateToken(user, ACCESS_TOKEN_VALIDATION);
    }

    public String generateRefreshToken(User user){      // refreshToken 발급
        return generateToken(user, REFRESH_TOKEN_VALIDATION);
    }

    public Boolean validateToken(String token, User user){      // 유효토큰 검사
        final String username = getUsernameFromToken(token);    // username에 to?????????
        return (username.equals(user.getLoginId()) && !isTokenExpired(token));
    }

    // 토큰 만료여부 확인
    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // 만료..?
    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // 토큰으로부터 온 모든 클레임 바디로 반환
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // ######################## BASE_64

    public String encoding(String str) {
        return Base64.getUrlEncoder().encodeToString(str.getBytes());
    }

    public String decoding(String str){
        return new String(Base64.getDecoder().decode(str));
    }

    // #########################
    // END


}
