package com.example.aispringboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.aispringboot.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
// 映射接口，用于数据库操作
// 映射接口的实现类，由 Mybatisatis-Plus 生成.   继承 extends BaseMapper<User> 即可。User 是实体类（数据表类）

public interface UserMapper extends BaseMapper<User> {

}
