package com.example.aispringboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
// 数据库用户实体类
@Data
@TableName("user") // mybatisplus 提供的注解， 用于指定数据库表名， 当类名和数据库表名不同时， 用这个注解指定数据库表名。 一样的时候可加可不加
public class User {
    // 用户id
    @TableId(type = IdType.AUTO) // mybatisplus 提供的注解， 用于指定数据库表的主键字段， 这里用自动递增策略， 数据库表的主键字段要设置为自增
    private Long id;

    // 用户名
    @TableField("username") // mybatisplus 提供的注解， 用于指定数据库表的字段名， 这里用username， 数据库表的字段名要设置为username
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 50, message = "用户名长度必须在3到50个字符之间")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户名只能包含字母、数字和下划线")
    private String username;


    // 密码
    @TableField("password")
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 50, message = "密码长度必须在6到50个字符之间")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "密码只能包含字母、数字和下划线")
    private String password;

    // 邮箱
    @TableField("email")
    @NotBlank(message = "邮箱不能为空")
    @Size(max = 100, message = "邮箱长度不能超过100个字符")
    @Pattern(regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "邮箱格式错误")
    private String email;

    // 昵称
    @TableField("nickname")
    @NotBlank(message = "昵称不能为空")
    @Size(min = 3, max = 50, message = "昵称长度必须在3到50个字符之间")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "昵称只能包含字母、数字和下划线")
    private String nickname;

    // 头像
    @TableField("avatar")
    private String avatar;

    // 手机号
    @TableField("phone")
    @NotBlank(message = "手机号不能为空")
    @Size(min = 11, max = 11, message = "手机号长度必须为11位")
    @Pattern(regexp = "^1[3456789]\\d{9}$", message = "手机号格式错误")
    private String phone;

    // 性别
    @TableField("gender")
    private Integer gender;

    // 用户类型
    @TableField("user_type")
    private Integer userType;

    // 状态
    @TableField("status")
    private Integer status;

    // 显示名称
    @TableField("display_name")
    private String displayName;

    // 创建时间
    @TableField("created_at")
    private String createdAt;

    // 更新时间
    @TableField("updated_at")
    private String updatedAt;

}
