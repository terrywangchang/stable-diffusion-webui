package com.keliu.data.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <h3>JWT 工具类</h3>
 * <p>
 * 负责 Token 的生成、解析、校验。
 * Token Payload 结构：userId、username、userType、roles。
 * </p>
 */
@Slf4j
@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.admin-expire}")
    private Long adminExpire;

    @Value("${jwt.staff-expire}")
    private Long staffExpire;

    // ==================== 生成 Token ====================

    /**
     * 生成管理员/领导 Token
     */
    public String generateAdminToken(Long userId, String username, String userType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        claims.put("userType", userType);
        return buildToken(claims, adminExpire);
    }

    /**
     * 生成从业人员 Token
     */
    public String generateStaffToken(Long userId, String fillId, Long ledgerId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("fillId", fillId);
        claims.put("ledgerId", ledgerId);
        claims.put("userType", "staff");
        return buildToken(claims, staffExpire);
    }

    private String buildToken(Map<String, Object> claims, Long expireSeconds) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expireSeconds * 1000))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    // ==================== 解析 Token ====================

    /**
     * 解析 Token 中的 Claims
     */
    public Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.warn("[JWT] 解析失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 从 Token 中获取用户ID
     */
    public Long getUserId(String token) {
        Claims claims = parseToken(token);
        return claims == null ? null : claims.get("userId", Long.class);
    }

    /**
     * 从 Token 中获取用户类型
     */
    public String getUserType(String token) {
        Claims claims = parseToken(token);
        return claims == null ? null : claims.get("userType", String.class);
    }

    // ==================== 校验 Token ====================

    /**
     * 判断 Token 是否有效
     */
    public boolean validateToken(String token) {
        Claims claims = parseToken(token);
        return claims != null && claims.getExpiration().after(new Date());
    }
}
