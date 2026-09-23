package com.example.aispringboot.DTO.response;

import lombok.Data;

@Data
public class UserLoginResponseDTO {
    private String token;
    private String roleType;
    private UserDetailResponseDTO userInfo;

    @Data
    public static class UserDetailResponseDTO {
         private String id;
         private String username;
         private String password;
         private String nickname;
         private String avatar;
         private String phone;
         private Integer gender; // 这里为什么用 Int 的包装类型， 可以用Int 类型么， 可以用Int 类型， 但是Int 类型不能用null。包装类型可以为null。
         private String genderDisplayName;
         private Integer userType;
         private Integer status;
         private String statusDisplayName;
         private String displayName;
         private String createdAt;
         private String updatedAt;
    }


}
