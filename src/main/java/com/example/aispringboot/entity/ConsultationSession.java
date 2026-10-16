package com.example.aispringboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@TableName("consultation_session")
public class ConsultationSession {
    @TableId("user_id") // 用户ID
    private Long userId;

    @TableId("session_title") // 会话标题
    @Size(max = 200, message = "会话标题长度不能超过200个字符")
    private String sessionTitle;

    @TableId("started_at") //
    private LocalDateTime startedAt;

    @TableId("last_emotion_analysis") // 最后一次情绪分析结果(JSON格式)
    private String lastEmotionAnalysis;

    @TableId("last_emotion_updated_at") // 最后一次情绪分析更新时间
    private LocalDateTime lastEmotionUpdatedAt;


}
