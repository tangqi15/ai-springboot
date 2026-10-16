package com.example.aispringboot.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.aispringboot.AiService.StructOutPut;
import com.example.aispringboot.DTO.command.ConsultationSessionCreatDTO;
import com.example.aispringboot.common.Result;
import com.example.aispringboot.util.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/psychological-chat")
public class PsychologicalChat {

    @PostMapping("/session/start")
    public Result<StructOutPut.StreamChatSession> startSession(@Valid @RequestBody ConsultationSessionCreatDTO creatDTO) {
        // 获取当前用户

        // 解决如何从 token中 解析出用户ID
        String token = JwtTokenUtil.getCurrentToken();

        DecodedJWT jwt = JwtTokenUtil.verifyToken(token);

        Long userId = jwt.getClaim("userId").asLong();
        System.out.println(userId + "userid + session/start 接口获得");

        return null;
    }
}
