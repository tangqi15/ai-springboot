package com.example.aispringboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component // 注册成 Bean
// 加载配置文件中 jwt开头的内容，自动赋值到 这个类
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private String secret;
    private long expiration;
    private long refreshExpiration;
    private String header;
    private String tokenPrefix;
}
