package com.etc.boot.Service;

import com.etc.boot.Pojo.GetGrade;
import com.etc.boot.mapper.IGradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService implements IGradeService {
    @Autowired
    private IGradeMapper gradeMapper;

    @Override
    public List<GetGrade> getGradeAll() {
        return gradeMapper.getGradeAll();
    }

    @Override
    public List<GetGrade> getGradeStu(int studentId) {
        return gradeMapper.getGradeStu(studentId);
    }

    @Override
    public List<GetGrade> getGradesByTeacher(String teacherId) {
        return gradeMapper.getGradesByTeacher(teacherId);
    }

    @Override
    public int addGrade(int studentId, int courseId, int grade) {
        return gradeMapper.addGrade(studentId, courseId, grade);
    }

    @Override
    public int updateGrade(int studentId, int courseId, int grade) {
        return gradeMapper.updateGrade(studentId, courseId, grade);
    }

    @Override
    public int deleteGrade(int studentId, int courseId) {
        return gradeMapper.deleteGrade(studentId, courseId);
    }

    @Override
    public boolean validateTeacherCourse(String teacherId, int courseId) {
        return gradeMapper.validateTeacherCourse(teacherId, courseId) > 0;
    }
}
