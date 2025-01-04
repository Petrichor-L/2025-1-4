package com.etc.boot.Pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 成绩实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    private Integer id;
    private Integer studentId;    // 学生ID
    private Integer courseId;     // 课程ID
    private Double score;         // 成绩
    private String semester;      // 学期
    private String examTime;      // 考试时间
} 