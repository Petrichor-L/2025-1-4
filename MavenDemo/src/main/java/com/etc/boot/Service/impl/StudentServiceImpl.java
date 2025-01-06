package com.etc.boot.Service.impl;

import com.etc.boot.Pojo.Course;
import com.etc.boot.Pojo.GetGrade;
import com.etc.boot.Service.StudentService;
import com.etc.boot.mapper.IGradeMapper;
import com.etc.boot.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    
    @Autowired
    private IGradeMapper gradeMapper;
    
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<GetGrade> getStudentGrades(String studentId) {
        return gradeMapper.getGradeStu(studentId);
    }

    @Override
    public List<Course> getStudentCourses(String studentId) {
        return courseMapper.findStudentCourses(studentId);
    }
} 