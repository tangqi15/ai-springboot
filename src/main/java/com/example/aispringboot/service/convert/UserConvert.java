package com.example.aispringboot.service.convert;

import com.example.aispringboot.DTO.response.UserLoginResponseDTO;
import com.example.aispringboot.entity.User;

public class UserConvert {
    // 构建响应 DTO
    public static UserLoginResponseDTO.UserDetailResponseDTO(User user) {
        return UserLoginResponseDTO.UserDetailResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .phone(user.getPhone())
                .birthday(user.getBirthday())
                .gender(user.getGender())
                .genderDisplayName(getGenderDisplayName(user.getGender()))
                .userType(user.getUserType())
                .status(user.getStatus())
                .statusDisplayName(user.getStatusDisplayName())
                .displayName(user.getDisplayName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static String getGenderDisplayName(Integer gender) {
        if (gender == null) {
            return "未知";
        }

//        switch(gender) {
//            case 1:
//                return "男";
//            case 2:
//                return "女";
//            default:
//                return "未知";
//        }
        return switch (gender) {
            case 1 -> "男";
            case 2 -> "女";
            default -> "未知";
        };
    }
}
