package com.packl.cardatabase.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;


@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret; // Base64 인코딩된 비밀 키

    private Key key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 토큰 생성
    public String getToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // 1일 유효
                .signWith(key)
                .compact();
    }

    // HTTP 요청에서 JWT 토큰을 추출하고 사용자명 반환
    public String getAuthUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        String token = authHeader.substring(7); // "Bearer " 제거

        try {
            JwtParser parser = Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build();

            Jws<Claims> claimsJws = parser.parseSignedClaims(token);
            return claimsJws.getPayload().getSubject();
        } catch (JwtException e) {
            // 토큰이 유효하지 않거나 만료된 경우
            return null;
        }
    }

    /* 책보고 앴는데 잘 안되는 구문
    static final long EXPIRATIONTIME = 86400000;
    static final String PREFIX = "Bearer";

    // 비밀키 생성, 시연 목적으로만 이용
    // 운영 환경에서는 애플리케이션 구성에서 읽어들여야 함
    static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 서명된 jwt 토큰을 생성
    public String getToken(String username) {
        String token = Jwts.builder().
                setSubject(username).
                setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME)).
                signWith(key).compact();

        return token;
    }

    public String getAuthUser(HttpServletRequest request) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token.replace("Bearer ", ""))
                .getPayload();
        return claims.getSubject();


        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(token != null) {
            String user = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token.replace(PREFIX, "")).getBody().getSubejct();

            if(user != nul)
                return user;
        }

        return null;


    }
    */
}
