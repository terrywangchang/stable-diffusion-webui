package com.keliu.data.security;

import com.alibaba.fastjson2.JSON;
import com.keliu.data.common.result.Result;
import com.keliu.data.common.result.ResultCode;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <h3>JWT 登录拦截器</h3>
 * <p>
 * 校验请求头中的 Authorization Token：
 * 1. Token 为空 → 未登录
 * 2. Token 无效或过期 → 重新登录
 * 3. 校验通过 → 将用户信息写入 request 属性，供后续业务使用
 * </p>
 */
@Slf4j
@Component
public class JwtAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.prefix}")
    private String prefix;

    /**
     * 放行路径（登录接口、公开接口等）
     */
    public static final String[] WHITE_LIST = {
            "/auth/login",          // 密码登录
            "/auth/sms-login",      // 短信登录
            "/auth/wechat-login",   // 微信扫码登录
            "/auth/send-sms",       // 发送短信验证码
            "/auth/refresh-token",  // 刷新Token
            "/auth/register",       // 小程序首次登录完善信息
            "/doc.html",
            "/webjars/**",
            "/v2/api-docs/**",
            "/swagger-resources/**"
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // 白名单路径直接放行
        String uri = request.getRequestURI();
        for (String wl : WHITE_LIST) {
            if (uri.contains(wl)) {
                return true;
            }
        }

        // 获取 Token
        String authHeader = request.getHeader(header);
        if (authHeader == null || authHeader.isEmpty()) {
            writeError(response, ResultCode.UNAUTHORIZED);
            return false;
        }
        if (authHeader.startsWith(prefix)) {
            authHeader = authHeader.substring(prefix.length());
        }

        // 校验 Token
        Claims claims = jwtUtils.parseToken(authHeader);
        if (claims == null) {
            writeError(response, ResultCode.TOKEN_INVALID);
            return false;
        }
        if (claims.getExpiration().before(new java.util.Date())) {
            writeError(response, ResultCode.TOKEN_EXPIRED);
            return false;
        }

        // 将用户信息写入 request
        request.setAttribute("userId", claims.get("userId"));
        request.setAttribute("userType", claims.get("userType"));
        // 可选：IP 记录用于日志
        request.setAttribute("clientIp", getRemoteIp(request));

        return true;
    }

    /**
     * 写入错误响应
     */
    private void writeError(HttpServletResponse response, ResultCode rc) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(200);
        response.getWriter().write(JSON.toJSONString(Result.fail(rc)));
    }

    /**
     * 获取客户端真实IP
     */
    private String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip.split(",")[0].trim();
    }
}
