package com.example.aispringboot.service;

import com.example.aispringboot.entity.ConsultationMessage;
import com.example.aispringboot.mapper.ConsultationMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConsultationMessageService {
    @Autowired
    private ConsultationMessageMapper consultationMessageMapper;

    public ConsultationMessage saveMessage(Long sessionId, String content, String emotion_tag) {
        ConsultationMessage userMessage = ConsultationMessage.builder()
                .sessionId(sessionId)
                .senderType(1) // 1、 用户 2、 AI助手
                .messageType(1) // 消息类型 1:文本
                .content(content)
                .emotionTag(emotion_tag) // 情绪标签
                .createdAt(LocalDateTime.now())
                .build();


        consultationMessageMapper.insert(userMessage);
        return userMessage;
    }
}
