package com.etc.boot.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 获取成绩的返回类
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetGrade {
    private int studentId;
    private String studentName;
    private String courseName;
    private String grade;
}
