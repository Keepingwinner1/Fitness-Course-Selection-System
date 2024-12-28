package com.tongji.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private String secretKey = "TongJi123456SportCourseSystemHanKunFuLiZengHuiLiZiNingChengHongLeiJAVAHomework";  // 请替换为实际的密钥

    // 生成 Token
    public String generateToken(Integer userID) {
        return Jwts.builder()
                .setSubject(userID.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // 设置过期时间为 1 小时
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 使用 HMAC 签名算法
                .compact();
    }

    // 从 Token 中获取 Claims
    public Claims extractClaims(String token) {
        return Jwts.parser()  // 使用新的 parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 从 Token 中获取用户ID
    public Integer extractUserID(String token) {
        return Integer.valueOf(extractClaims(token).getSubject());
    }

    // 验证 Token 是否有效
    public boolean validateToken(String token, Integer userID) {
        Integer extractedUserID = extractUserID(token);
        return (extractedUserID.equals(userID) && !isTokenExpired(token));
    }

    // 判断 Token 是否过期
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private String updateToken(String token) {
        return generateToken(extractUserID(token));
    }
}
