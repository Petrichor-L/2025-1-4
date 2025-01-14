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
    private String courseId;     // 对应数据库 course_id
    private String courseName;   // 对应数据库 course_name
    private String classroom;    // 对应数据库 classroom
    private String teacherId;    // 修改为 String 类型，对应数据库 teacher_id varchar(50)
    @javax.persistence.Transient
    private String teacherName;  // 不对应数据库字段
} 