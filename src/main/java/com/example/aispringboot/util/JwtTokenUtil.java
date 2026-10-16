package com.example.aispringboot.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.aispringboot.config.JwtConfig;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

    // 提取token
    public static String extractTokenFromRequest(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        String headerToken = request.getHeader("token");
        // Spring 的 org.springframework.util.StringUtils
        // StringUtils.hasText(str) 用来判断字符串是否包含至少一个非空白字符。
        // 当 headerToken 中包含 字符串 null、“”、 “   ”、“\t\n(制表符、换行符)” 为false
        // "hello"、 “ hello ”  为true

        // 它可以安全处理 null，但只判断有没有内容，不代表内容就是合法的 Token。
        //        - null
        //        - 空字符串 ""
        //        - 全是空白字符，比如 "   "、"\t\n"

        // 所以它比只判断 str != null && !str.isEmpty() 多一步：全是空白也会返回 false。

        if (StringUtils.hasText(headerToken)) {
            return headerToken;
        }

        return null;
    }

    // 获取当前的token
    public static String getCurrentToken() {

        // (ServletRequestAttributes)  强制类型转换
         ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
         if (attributes != null) {
             // 获取到请求
             HttpServletRequest request = attributes.getRequest();
             String token = (String) request.getAttribute("jwtToken");

             if (StringUtils.hasText(token)) {
                 return token;
             }

             // 备用方案， 从请求头直接获取
             String headerToken = extractTokenFromRequest(request);
             return headerToken;
         }
         return null;
    };

    // 验证token
    public static TokenVerificationResult validateToken(String token) {
        DecodedJWT jwt = verifyToken(token);
        Long userId = jwt.getClaim("userId").asLong();
        String username = jwt.getClaim("username").asString();

        // 角色类型
        Integer roleType = null;
        try {
            roleType = jwt.getClaim("rokeType").asInt();
        } catch (Exception e) {
            String roleTypeStr = jwt.getClaim("rokeType").asString();
            if (StringUtils.hasText(roleTypeStr)) {
                roleType = Integer.valueOf(roleTypeStr);
            }
        }
        if (userId != null && StringUtils.hasText(username) && roleType != null) {
            return new TokenVerificationResult(userId, username, roleType, true);
        }

        return null;
    }

    // 验证token 有效性. verify 中文是 “验证” 或 “核实”。
    public static DecodedJWT verifyToken(String token) {
        if (!StringUtils.hasText(token)) {
            throw new JWTVerificationException("Token 不能为空");
        }
        // token 解码
        JwtConfig jwtConfig = getJwtConfig();
        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecret());

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();

        // 返回解码后的对象
        return verifier.verify(token);
    }

    // Token 验证结果 封装类
    @Getter
    public static class TokenVerificationResult {
        private final Long userId;
        private final String username;
        private final Integer rokeType;
        private final boolean vaild;

        public TokenVerificationResult(Long userId, String username, Integer rokeType, boolean vaild) {
            this.userId = userId;
            this.username = username;
            this.rokeType = rokeType;
            this.vaild = vaild;
        }
    }
}
