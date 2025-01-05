package com.etc.boot.Service;

import com.etc.boot.Pojo.GetGrade;
import com.etc.boot.mapper.IGradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService implements IGradeService {
    @Autowired
    private IGradeMapper iGradeMapper;

    @Override
    public List<GetGrade> getGradeAll() {
        return iGradeMapper.getGradeAll();
    }

    @Override
    public List<GetGrade> getGradeStu(int studentId) {
        return iGradeMapper.getGradeStu(studentId);
    }

    @Override
    public int addGrade(int studentId, int courseId, int grade) {
        return iGradeMapper.addGrade(studentId, courseId, grade);
    }
}
