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
    // 课程id
    private int courseId;
    // 课程名
    private String courseName;
    // 教室
    private String classroom;
    // 教师id
    private int teacherId;
} 