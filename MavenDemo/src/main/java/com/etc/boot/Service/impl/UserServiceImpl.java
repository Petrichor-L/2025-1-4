package com.etc.boot.Service.impl;

import com.etc.boot.Pojo.User;
import com.etc.boot.Service.UserService;
import com.etc.boot.dto.RegisterDTO;
import com.etc.boot.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public User login(String username, String password) {
        log.info("尝试登录，用户名：{}", username);
        if (username == null || password == null) {
            return null;
        }
        User user = userMapper.findByUsernameAndPassword(username, password);
        log.info("查询结果：{}", user);
        return user;
    }

    @Override
    @Transactional
    public User register(RegisterDTO registerDTO) {
        log.info("开始注册用户，接收到的数据：{}", registerDTO);
        
        // 检查用户名是否已存在
        if (checkUsernameExist(registerDTO.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 创建新用户对象
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setName(registerDTO.getName());
        user.setPassword(registerDTO.getPassword());
        user.setRole(registerDTO.getRole());
        user.setPhone(registerDTO.getPhone());
        
        log.info("准备插入数据库的用户对象：{}", user);
        
        // 保存用户并检查结果
        int rows = userMapper.insert(user);
        log.info("数据库插入结果：{}", rows);
        
        if (rows != 1) {
            throw new RuntimeException("注册失败，请重试");
        }
        
        return user;
    }

    @Override
    public boolean checkUsernameExist(String username) {
        return userMapper.findByUsername(username) != null;
    }
} 