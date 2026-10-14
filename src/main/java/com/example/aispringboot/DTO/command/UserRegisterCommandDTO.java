package com.example.aispringboot.DTO.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

//登录参数校验
@Data
public class UserRegisterCommandDTO {
    // 用户名
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 50, message = "用户名长度不能超过50个字符")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户名只能包含字母、数字和下划线")
    private String username;

    // 邮箱
    @NotBlank(message = "邮箱不能为空")
    @Size(max = 100, message = "邮箱长度不能超过100个字符")
    @Pattern(regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+\\.[a-z邮箱A-Z0-9-.]+$", message = "格式错误")
    private String email;

    // 昵称
    @Size(max = 50, message = "昵称长度不能超过50个字符")
    private String nickname;


    // 手机号
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    private String phone;

   // 密码
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 50, message = "密码长度不能超过100个字符")
    private String password;


    // 确认密码
    @NotBlank(message = "确认密码不能为空")
    @Size(min = 6, max = 50, message = "密码长度不能超过100个字符")
    private String confirmPassword;

    private Integer gender;
    private LocalDate birthday;
    private Integer userType = 1;

}
