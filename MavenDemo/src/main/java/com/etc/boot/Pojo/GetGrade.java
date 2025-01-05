package com.etc.boot.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 获取成绩的返回类
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetGrade {
    private String studentId;    // 改为 String
    private Integer courseId;
    private Integer grade;
    private String studentName;
    private String courseName;
}
