package com.example.aispringboot.DTO.response;

import lombok.Data;

@Data
public class PsychologicalSupportResponseDTO {
//      "sessionId": "session_28977",
//              "userHash": 5,
//              "initialMessage": "今天天气很好，心情还不错",
//              "startTime": 1781503850583,
//              "expiryTime": 1781590250583,
//              "status": "ACTIVE",
//              "messageCount": 1


    private Integer userHash;
    private String initialMessage;
    private String startTime;
    private String expiryTime;
    private String status;
    private Integer messageCount;
}
