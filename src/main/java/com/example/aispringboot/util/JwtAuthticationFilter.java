package com.example.aispringboot.util;

import ch.qos.logback.core.util.StringUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.example.aispringboot.DTO.response.UserLoginResponseDTO;
import com.example.aispringboot.common.ResultCode;
import com.example.aispringboot.config.SecurityConfig;
import com.example.aispringboot.entity.User;
import com.example.aispringboot.enumClass.UserStatus;
import com.example.aispringboot.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

// 代表直只执行一次 OncePerRequestFilter
public class JwtAuthticationFilter extends OncePerRequestFilter {

    // 引入 UserService
    @Resource
    private UserService userService;

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
                // 3、 查询用户信息 验证用户的状态
                UserLoginResponseDTO.UserDetailResponseDTO user = userService.getUserById(validationResult.getUserId());
                System.out.println(JSONUtil.parseObj(user) + "useruser");

                if (user != null && UserStatus.NORMAL.getCode().equals(user.getStatus())) {
                    // 4、 创建 Spring Security 认证对象
                    // 单元树权限列表
                    List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                            new SimpleGrantedAuthority("ROLE_" + validationResult.getRokeType())
                    );

                    // 5、创建 UsernamePasswordAuthenticationToken 对象
                    UsernamePasswordAuthenticationToken authcation = new UsernamePasswordAuthenticationToken(
                            validationResult.getUsername(), // 用户名作为主体
                            null,
                            authorities
                    );

                    // 6、 设置认证信息到 Spring Security 上下文
                    SecurityContextHolder.getContext().setAuthentication(authcation);

                    // 7、 将token 存储到 请求的属性中
                    request.setAttribute("jwtToken", token);
                } else {
                    clearSecurityContext();
                    ResponseUtil.writeError(response, ResultCode.TOKEN_ACCESS_FORBIDDEN);
                }

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
