package com.example.aispringboot.service.convert;

import com.example.aispringboot.DTO.command.UserRegisterCommandDTO;
import com.example.aispringboot.DTO.response.UserLoginResponseDTO;
import com.example.aispringboot.entity.User;
import com.example.aispringboot.enumClass.UserStatus;

import java.time.LocalDate;

public class UserConvert {

    // 构建响应 DTO 这里说的是 detail
    public static UserLoginResponseDTO.UserDetailResponseDTO entityToDetailResponse(User user) {
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
                .userTypeDisplayName(user.getUserTypeDisplayName())
                .status(user.getStatus())
                .statusDisplayName(user.getStatusDisplayName())
                .displayName(user.getDisplayName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    // 这是 返回的完整结构
    public static UserLoginResponseDTO entityToLoginResponse(String token,UserLoginResponseDTO.UserDetailResponseDTO userInfo) {
        return UserLoginResponseDTO.builder()
                .token(token)
                .roleType(userInfo.getUserType().toString())
                .userInfo(userInfo)
                .build();
    }


    // 转换类， 将参数转换成 数据库识别的数据
    public static User registerCommandToEntity(UserRegisterCommandDTO userRegisterCommandDTO, String encodePassword) {
        return User.builder()
                .username(userRegisterCommandDTO.getUsername())
                .email(userRegisterCommandDTO.getEmail())
                .phone(userRegisterCommandDTO.getPhone())
                .password(encodePassword)
                .nickname(userRegisterCommandDTO.getNickname())
                .gender(userRegisterCommandDTO.getGender())
                .birthday(userRegisterCommandDTO.getBirthday())
                .userType(userRegisterCommandDTO.getUserType())
                .status(UserStatus.NORMAL.getCode())
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
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
