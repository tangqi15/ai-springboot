package com.example.aispringboot.service.convert;

import com.example.aispringboot.DTO.command.ConsultationSessionCreatDTO;
import com.example.aispringboot.DTO.response.PsychologicalSupportResponseDTO;
import com.example.aispringboot.entity.ConsultationSession;

import java.time.LocalDate;

public class ConsultationSessionConvert {

    // 插入数据转换
    public static ConsultationSession sessionToEntity(Long userId, ConsultationSessionCreatDTO creatDTO) {
        return ConsultationSession.builder()
                .userId(userId)
                .sessionTitle(creatDTO.getSessionTitle())
                .startedAt(LocalDate.now())
//                .lastEmotionAnalysis(LocalDate.now())
//                .lastEmotionUpdatedAt(LocalDate.now())
                .build();
    }

    // 返回转换
    public static PsychologicalSupportResponseDTO entityToPsychologicalSupportResponse () {

    }
}
