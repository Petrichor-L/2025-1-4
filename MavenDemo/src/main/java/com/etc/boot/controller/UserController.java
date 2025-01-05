package com.etc.boot.controller;

import com.etc.boot.Service.UserService;
import com.etc.boot.common.Result;
import com.etc.boot.dto.LoginDTO;
import com.etc.boot.dto.RegisterDTO;
import com.etc.boot.Pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<User> login(@RequestBody LoginDTO loginDTO) {
        log.info("收到登录请求：{}", loginDTO);
        try {
            if (StringUtils.isEmpty(loginDTO.getUsername()) || 
                StringUtils.isEmpty(loginDTO.getPassword())) {
                return Result.error("用户名或密码不能为空");
            }
            
            User user = userService.login(loginDTO.getUsername(), loginDTO.getPassword());
            if (user != null) {
                return Result.success(user);
            }
            return Result.error("用户名或密码错误");
            
        } catch (Exception e) {
            log.error("登录失败：", e);
            return Result.error("登录失败：" + e.getMessage());
        }
    }
    
    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<User> register(@RequestBody RegisterDTO registerDTO) {
        log.info("接收到的注册数据: {}", registerDTO);
        try {
            // 参数校验
            if (registerDTO == null) {
                return Result.error("请求参数不能为空");
            }
            if (StringUtils.isEmpty(registerDTO.getUsername()) || 
                StringUtils.isEmpty(registerDTO.getPassword())) {
                return Result.error("用户名或密码不能为空");
            }
            
            User user = userService.register(registerDTO);
            log.info("注册成功，返回用户数据: {}", user);
            return Result.success(user);
            
        } catch (Exception e) {
            log.error("注册失败，错误：", e);
            return Result.error(e.getMessage());
        }
    }
}