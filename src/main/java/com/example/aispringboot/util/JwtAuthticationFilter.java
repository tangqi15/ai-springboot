package com.example.aispringboot.util;

import com.example.aispringboot.config.SecurityConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
                                    FilterChain filterChain) throws ServletException, IOException {

        // 获取请求的URI 和 方法
        String requestUri = request.getRequestURI();
        String method = request.getMethod();

        System.out.println(requestUri);
        System.out.println(method);
    }
}
