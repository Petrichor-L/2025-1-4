package com.etc.boot.Pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.Transient;

/**
 * 课程实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private Integer id;
    private String courseId;     // 对应数据库 course_id
    private String courseName;   // 对应数据库 course_name
    private String classroom;    // 对应数据库 classroom
    private String teacherId;    // 对应数据库 teacher_id
    @Transient
    private String teacherName;  // 不对应数据库字段
} 