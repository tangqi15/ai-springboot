package com.example.aispringboot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.aispringboot.DTO.command.UserLoginCommandDTO;
import com.example.aispringboot.DTO.response.UserLoginResponseDTO;
import com.example.aispringboot.common.Result;
import com.example.aispringboot.entity.User;
import com.example.aispringboot.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Resource // 注入 UserMapper 接口的实现类
    private UserMapper userMapper;


    public Result<UserLoginResponseDTO> login(UserLoginCommandDTO commandDTO) {
        // service 逻辑处理

        // 构建查询条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();

        // 创建对应的查询条件
        queryWrapper.eq(User::getUsername, commandDTO.getUsername()).or()
                .eq(User::getEmail, commandDTO.getEmail());

        // 调用 mybatis-plus 查询用户
        User user = userMapper.selectOne(queryWrapper);

        System.out.println(user);

//        if (user == null) {
//            return Result.fail("用户不存在");
//        }

        return Result.ok(new UserLoginResponseDTO());
    }
}
