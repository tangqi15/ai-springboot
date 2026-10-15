package com.example.aispringboot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.aispringboot.DTO.command.UserLoginCommandDTO;
import com.example.aispringboot.DTO.command.UserRegisterCommandDTO;
import com.example.aispringboot.DTO.response.UserLoginResponseDTO;
import com.example.aispringboot.common.Result;
import com.example.aispringboot.entity.User;
import com.example.aispringboot.enumClass.UserType;
import com.example.aispringboot.exception.BusinessException;
import com.example.aispringboot.mapper.UserMapper;
import com.example.aispringboot.service.convert.UserConvert;
import com.example.aispringboot.util.JwtTokenUtil;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Resource // 注入 UserMapper 接口的实现类
    private UserMapper userMapper;
    // 这是创建一个 BCrypt 密码加密（更准确说：哈希）工具对象，用于安全地保存和校验用户密码。
    //它的作用 不要把用户密码直接明文存入数据库：
    //123456 而是存成 BCrypt 哈希值，例如：$2a$10$7fWzHvL9b...
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    // 登录
    public UserLoginResponseDTO login(UserLoginCommandDTO commandDTO) {
        // service 逻辑处理

        // 构建查询条件。User 是实体类（数据表类）
        // LambdaQueryWrapper 是 Mybatisatis-Plus 提供的查询条件构建器， 用于构建查询条件。
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();

        // 创建对应的查询条件
        queryWrapper.eq(User::getUsername, commandDTO.getUsername()).or()
                .eq(User::getEmail, commandDTO.getEmail());

        // 调用 mybatis-plus 查询用户
        User user = userMapper.selectOne(queryWrapper);

        System.out.println(user + "user");

        if (user == null) {
            // 当用户不存在，抛出异常， 由 controller 处理。  业务异常
            throw new BusinessException("用户不存在");
        }
        // 验证密码是否正确
        String inputPassword = commandDTO.getPassword().trim();
        if(!bCryptPasswordEncoder.matches(inputPassword, user.getPassword())) {
            throw new BusinessException("密码错误");
        }

        // 检查用户的状态
//       if(user.getStatus() != 1) {
        if (!user.isActive()) {
            throw new BusinessException("用户已被禁用, 请联系管理员");
        }

        // 生成JWT token
        String token = JwtTokenUtil.generateToken(user.getId(), user.getUsername(), user.getUserType());

        System.out.println(token);

        UserLoginResponseDTO.UserDetailResponseDTO userInfo = UserConvert.entityToDetailResponse(user);
//        UserLoginResponseDTO data = UserConvert.entityToLoginResponse(token, userInfo);
//        return Result.ok(data);
        return UserConvert.entityToLoginResponse(token, userInfo);
    }

    // 注册
    public UserLoginResponseDTO.UserDetailResponseDTO register(UserRegisterCommandDTO commandDTO) {

        // 验证密码是否一致
        if (!commandDTO.getPassword().equals(commandDTO.getConfirmPassword())) {
            throw  new BusinessException("两次密码输入不一致");
        }

        // 检查用户名是否存在
        LambdaQueryWrapper<User> usernameQuery = new LambdaQueryWrapper<User>();
        usernameQuery.eq(User::getUsername, commandDTO.getUsername());
//        法1: 查询对象
//        User user = userMapper.selectOne(queryWrapper);
//        if (user != null) {
//            throw new BusinessException("用户名已存在");
//        }

//        法2 查询数量
        if(userMapper.selectCount(usernameQuery) > 0) {
            throw new BusinessException("用户名已存在");
        }

        // 检查邮箱是否存在
        LambdaQueryWrapper<User> emailQuery = new LambdaQueryWrapper<User>();
        emailQuery.eq(User::getEmail, commandDTO.getEmail());
        Long count = userMapper.selectCount(emailQuery);
        if (count > 0) {
            throw new BusinessException("邮箱已存在");
        }

        // 用户类型 是否在枚举中
        if(!UserType.isValidCode(commandDTO.getUserType())) {
            throw new BusinessException("无效的用户类型");
        }


        // 密码加密
        String password = commandDTO.getPassword().trim();
        String encodePassword = bCryptPasswordEncoder.encode(password);

        // 创建用户  这里的数据是不是数据库能识别的数据， 需要一个转换类，转换成 数据库能识别的数据
        User user = UserConvert.registerCommandToEntity(commandDTO, encodePassword);

        // 插入数据库
        userMapper.insert(user);
        return UserConvert.entityToDetailResponse(user);
    }

    // 根据用户id,查询用户信息
    public UserLoginResponseDTO.UserDetailResponseDTO getUserById(Long userId) {

        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        return UserConvert.entityToDetailResponse(user);
    }
}
