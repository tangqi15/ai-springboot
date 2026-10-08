package com.example.aispringboot.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.aispringboot.config.JwtConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil implements ApplicationContextAware {
    // 定义签发者
    private static final String ISSUER = "mental-health-assistant";

    private static ApplicationContext applicationContext;
    // 用于在 静态工具类中 获取Spring 容器管理的Bean
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        JwtTokenUtil.applicationContext = applicationContext;
    }

    private static JwtConfig getJwtConfig() {
        // getBean 获取 spring ioc 容器里面的 bean
        return (JwtConfig) applicationContext.getBean(JwtConfig.class);
    }


    // 生成token 的方法
    public static String generateToken(Long userId, String username, Integer rokeType) {
       try {
           // 生成 token 的逻辑
           // 第一步 获取 jwt 配置
           JwtConfig jwtConfig = getJwtConfig();
           // 第二步 生成签名的算法
           Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecret());
           // 第三步 生成过期时间
           Date expiration = new Date(System.currentTimeMillis() + jwtConfig.getExpiration());

           String token = JWT.create()
                   .withClaim("userId", userId)
                   .withClaim("username", username)
                   .withClaim("rokeType", rokeType)
                   .withExpiresAt(expiration) // 设置过期时间
                   .withIssuedAt(new Date()) // 设置签发时间
                   .withIssuer(ISSUER) // 设置签发者
                   .sign(algorithm); // 签名内容

           return token;
       } catch (Exception e) {
           throw new RuntimeException("生成token 失败： " + e);
       }
    }
}
