package com.example.aispringboot.controller;

import cn.hutool.json.JSONUtil;
import com.example.aispringboot.DTO.command.UserRegisterCommandDTO;
import com.example.aispringboot.DTO.command.UserLoginCommandDTO;
import com.example.aispringboot.DTO.response.UserLoginResponseDTO;
import com.example.aispringboot.common.Result;
import com.example.aispringboot.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class User {

    @Resource // 注入 UserService 实现类
    private UserService userService;

    // 用户登录接口
    @PostMapping("/login")
    public Result<UserLoginResponseDTO> login(@Valid @RequestBody UserLoginCommandDTO commandDTO) {
        System.out.println(commandDTO.getUsername());
        System.out.println(commandDTO.getPassword());

        // 调用 service 层的 login 方法
        UserLoginResponseDTO result = userService.login(commandDTO);
        System.out.println(result);

        return Result.ok(result);
    }

    // 用户注册接口
    @PostMapping("/add")
    public Result<UserLoginResponseDTO.UserDetailResponseDTO> register(@Valid @RequestBody UserRegisterCommandDTO addCommandDTO) {
        System.out.println(addCommandDTO);

        // cn.hutool 携带的 转成json 的工具类
        System.out.println(JSONUtil.parseObj(addCommandDTO));


        UserLoginResponseDTO.UserDetailResponseDTO result = userService.register(addCommandDTO);

        return Result.ok(result);
    }

    // 获取当前用户接口  经过jwt认证
    @GetMapping("/current")
    public Result<UserLoginResponseDTO.UserDetailResponseDTO> getCurrentUser() {
        // 解决如何从 token中 解析出用户ID
        return null;
    }
}
