package com.example.aispringboot.AiService;

import com.example.aispringboot.DTO.command.ConsultationSessionCreatDTO;
import com.example.aispringboot.entity.ConsultationSession;
import com.example.aispringboot.mapper.ConsultationSessionMapper;
import com.example.aispringboot.service.ConsultationSessionService;
import com.example.aispringboot.service.convert.ConsultationSessionConvert;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PsychologicalSupportService {

    @Autowired
    private ConsultationSessionService consultationSessionService;

    public StructOutPut.StreamChatSession startSession(Long userId, ConsultationSessionCreatDTO createDTO) {

        // 创建数据库会话记录
        ConsultationSession consultationSession = consultationSessionService.createSession(userId, createDTO);

        return null;
    }
}
