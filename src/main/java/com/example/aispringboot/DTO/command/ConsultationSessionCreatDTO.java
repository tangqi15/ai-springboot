package com.example.aispringboot.DTO.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ConsultationSessionCreatDTO {
    @Size(max = 200, message = "回话标题最多200个字符")
    private String sessionTitle;

    @NotBlank
    @Size(max = 2000, message = "初始消息最多2000个字符")
    private String initialMessage;
}
