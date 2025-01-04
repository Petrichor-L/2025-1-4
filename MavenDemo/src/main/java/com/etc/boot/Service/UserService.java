package com.etc.boot.Service;

import com.etc.boot.Pojo.Users;
import com.etc.boot.dto.RegisterDTO;

public interface UserService {
    /**
     * 用户登录
     * @return null表示登录失败
     */
    Users login(String username, String password);

    /**
     * 用户注册
     * @throws RuntimeException 当用户名已存在时抛出异常
     */
    Users register(RegisterDTO registerDTO);

    /**
     * 检查用户名是否存在
     */
    boolean checkUsernameExist(String username);
} 