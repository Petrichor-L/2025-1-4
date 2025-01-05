package com.etc.boot.Pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    // 自增主键
    private Integer id;
    // 账号
    private String username;
    // 姓名
    private String name;
    // 密码
    private String password;
    // 角色枚举：admin teacher student
    private String role;
    // 手机号
    private String phone;
} 