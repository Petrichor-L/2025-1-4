package com.etc.boot.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 获取成绩的返回类
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetGrade {
    private Integer studentId;    // 学生ID
    private Integer courseId;     // 课程ID
    private Integer grade;        // 成绩
    private String studentName;   // 学生姓名（关联users表）
    private String courseName;    // 课程名称（关联course表）
}
