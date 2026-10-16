package com.example.aispringboot.AiService;

public class StructOutPut {
    // 之前是通过 data 方式实现的。 对于这种不变的数据，我们还可以通过 record 实现

    public record StreamChatSession(
             // 定义响应体的字段
             String sessionId,
             Long userHash,
             String initialMessage,
             Long startTime,
             Long expiryTime,
             Integer messageCount,
             String status) {}

}
