package com.etc.boot.Service;

import com.etc.boot.Pojo.GetGrade;
import com.etc.boot.mapper.IGradeMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TeacherService implements ITeacherService {
    @Autowired
    private IGradeMapper iGradeMapper;


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
}
