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
    private String courseId;     // 课程编号
    private String courseName;   // 课程名称
    private String classroom;    // 教室
    private String teacherId;    // 教师ID（关联users表的username）
    private String teacherName;  // 教师姓名（从users表获取）
} 