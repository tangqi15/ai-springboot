package com.example.aispringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

// 注解：  当前是一个配置类
@Configuration

// 开启Web安全
@EnableWebSecurity

// 开启方法安全
@EnableMethodSecurity

public class SecurityConfig {

    private static final String[] PUBLIC_PATHS = {
            "/",
            "/api/test",
            "/api/user/login"
    };

    // 注解：  定义一个Bean，用于配置Web安全
    // 下面定义的内容 会作为一个 Bean 的对象， 后面在其他地方可以直接使用， 他是一个自动注入的过程
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 禁用CSRF保护, API 服务通常是不需要的, 因为 API 服务通常是无状态的, 不需要保护 CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // 配置会话管理为 无状态 （JWT需要）
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 配置请求的授权规则
                .authorizeHttpRequests(auth -> auth
                    // 公开路径，无需登录即可访问
                    .requestMatchers(PUBLIC_PATHS).permitAll()
                    // 其他路径，都需要登录
                    .anyRequest().authenticated()
                );
        return http.build();
    }
}
