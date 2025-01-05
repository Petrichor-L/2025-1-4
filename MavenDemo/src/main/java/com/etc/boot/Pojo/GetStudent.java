package com.etc.boot.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStudent {
    // 自增主键
    private Integer studentId;
    // 账号
    private String username;
    // 姓名
    private String name;
    // 手机号
    private String phone;
}
