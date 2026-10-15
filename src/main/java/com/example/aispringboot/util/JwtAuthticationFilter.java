package com.example.aispringboot.util;

import ch.qos.logback.core.util.StringUtil;
import com.example.aispringboot.common.ResultCode;
import com.example.aispringboot.config.SecurityConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 代表直只执行一次 OncePerRequestFilter
public class JwtAuthticationFilter extends OncePerRequestFilter {

    @Override
    // 不需要过滤的方法. request 拿到当前请求
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 获取url
        String requestURI = request.getRequestURI();
        // 检查是否公开路径
        return SecurityConfig.isPublicPATH(requestURI);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        // 获取请求的URI 和 方法
        String requestUri = request.getRequestURI();
        String method = request.getMethod();

        System.out.println(requestUri);
        System.out.println(method);

        //  1、提取jwt token
        String token = JwtTokenUtil.extractTokenFromRequest(request);
        if (StringUtils.hasText(token)) {
            //  2、验证 JWT token 并获取用户信息
            JwtTokenUtil.TokenVerificationResult validationResult = JwtTokenUtil.validateToken(token);

            if (validationResult != null && validationResult.isVaild()) {

            } else {
                // 清理上下文
                clearSecurityContext();
                ResponseUtil.writeError(response, ResultCode.TOKEN_INVALID );
                return;
            }

        } else {
            // 清理上下文
            clearSecurityContext();
            ResponseUtil.writeError(response, ResultCode.ACCESS_UNAUTHORIZED);
            return;
        }
        // 继续 过滤器链
        chain.doFilter(request, response);

        //  3、
        //  4、
        //  5、
        //  6、
        //  7、
        //  8、


    }

    // 清理String Security 安全框架的上下文
    private void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }
}
