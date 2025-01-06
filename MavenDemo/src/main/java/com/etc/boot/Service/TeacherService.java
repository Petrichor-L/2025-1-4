package com.etc.boot.Service;

import com.etc.boot.Pojo.Course;
import com.etc.boot.Pojo.GetGrade;
import com.etc.boot.mapper.CourseMapper;
import com.etc.boot.mapper.IGradeMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TeacherService implements ITeacherService {
    @Autowired
    private IGradeMapper iGradeMapper;
    @Autowired
    private CourseMapper courseMapper;


    @Override
    public List<GetGrade> getGrade(String teacherId) {
        return iGradeMapper.getGradesByTeacher(teacherId);
    }


    @Override
    public int addGrade(String studentId, int courseId, int grade) {
        return iGradeMapper.addGrade(studentId, courseId, grade);
    }

    @Override
    public int linkStuCourse(String studentId, int courseId) {
        return iGradeMapper.linkStuCourse(studentId, courseId);
    }

    @Override
    public int upGrade(String studentId, int courseId, int grade) {
        return iGradeMapper.updateGrade(studentId, courseId, grade);
    }

    @Override
    public List<Course> findTeacherCourses(String teacherId) {
        return courseMapper.findTeacherCourses(teacherId);
    }

//    @Override
//    public List<Course> searchCourses(String keyword) {
//        return courseMapper.searchCourses(keyword);
//    }
//
//    @Override
//    public List<Course> getAllCourses() {
//        return null;
//    }
}
