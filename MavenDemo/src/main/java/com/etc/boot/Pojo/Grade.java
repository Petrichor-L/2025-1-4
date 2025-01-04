package com.etc.boot.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 成绩表
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
    // 学生的id
    private int studentId;
    // 课程的id
    private int courseId;
    // 成绩
    private int grade;
}
