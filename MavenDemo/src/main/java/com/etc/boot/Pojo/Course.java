package com.etc.boot.Pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 课程实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private Integer id;
    private String courseName;    // 课程名称
    private String courseCode;    // 课程代码
    private Integer credit;       // 学分
    private String teacherId;     // 授课教师ID
    private String description;   // 课程描述
} 