package com.fastcampus.sns.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtTokenUtils {

    public static String generateAccessToken(String username, String key, long expiredTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("username", username);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
            .signWith(getKey(key), SignatureAlgorithm.HS256)
            .compact();
    }

    public static String getUsername(String token, String key) {
        return extractClaims(token, key).get("username", String.class);
    }

    private static Claims extractClaims(String token, String key) {
        return Jwts.parserBuilder()
            .setSigningKey(getKey(key))
            .build()
            .parseClaimsJws(token)
            .getBody();
    }


    // 비밀키 생성
    private static Key getKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
