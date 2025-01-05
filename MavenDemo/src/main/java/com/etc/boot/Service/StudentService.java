package com.etc.boot.Service;

import com.etc.boot.Pojo.Course;
import com.etc.boot.Pojo.GetGrade;
import java.util.List;

public interface StudentService {
    // 获取学生成绩
    List<GetGrade> getStudentGrades(String studentId);
    
    // 获取学生选修的课程
    List<Course> getStudentCourses(String studentId);
}
