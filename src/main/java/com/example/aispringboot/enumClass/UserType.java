package com.example.aispringboot.enumClass;

import lombok.Getter;

@Getter
public enum UserType {

    USER(1, "普通用户"),
    ADMIN(2, "管理员");

    private final Integer code;
    private final String description;
    UserType(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    /* *
     * 根据代码获取枚举
     */
    public static UserType fromCode(Integer code) throws IllegalAccessException {
        for (UserType userType : UserType.values()) {
            if (userType.code.equals(code)) {
                return userType;
            }
        }
        throw new IllegalAccessException("未知的用户类型代码： " + code);
    }

    /* *
    验证用户类型代码是否有效
     */
    public static boolean isValidCode(Integer code) {
        for (UserType userType : UserType.values()) {
            if (userType.code.equals(code)) {
                return true;
            }
        }
        return false;
    }


}
