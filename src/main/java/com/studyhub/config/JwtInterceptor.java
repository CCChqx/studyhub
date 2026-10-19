package com.studyhub.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studyhub.common.ErrorCode;
import com.studyhub.common.Result;
import com.studyhub.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

/**
 * 认证拦截器。
 * app.auth.enabled=false 时直接放行，开发期调试核心业务无需 token。
 */
@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    @Value("${app.auth.enabled:false}")
    private boolean authEnabled;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 认证开关：关闭时放行，测试核心业务永远不用带 token
        if (!authEnabled) {
            return true;
        }

        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            return reject(response, "未登录或 token 缺失");
        }

        String token = auth.substring(7);
        try {
            Claims claims = jwtUtil.parseToken(token);
            if (!"access".equals(claims.get("type"))) {
                return reject(response, "token 类型错误，请使用 access token");
            }
            Long userId = Long.valueOf(claims.getSubject());
            request.setAttribute("userId", userId);
            request.setAttribute("username", claims.get("username"));
            return true;
        } catch (Exception e) {
            return reject(response, "token 无效或已过期");
        }
    }

    private boolean reject(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(Result.error(ErrorCode.UNAUTHORIZED, msg)));
        return false;
    }
}
