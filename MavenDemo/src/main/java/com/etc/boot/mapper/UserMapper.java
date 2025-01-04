package com.etc.boot.mapper;

import com.etc.boot.Pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    
    /**
     * 根据用户名和密码查询用户
     */
    @Select("SELECT * FROM users WHERE username = #{username} AND password = #{password}")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    
    /**
     * 根据用户名查询用户
     */
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(@Param("username") String username);
    
    /**
     * 插入新用户
     */
    @Insert("INSERT INTO users (username, name, password, role, phone) " +
            "VALUES (#{username}, #{name}, #{password}, #{role}, #{phone})")
    int insert(User user);
} 